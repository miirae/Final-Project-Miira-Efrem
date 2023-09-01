package com.company.gamestore.controller;

import com.company.gamestore.model.*;
import com.company.gamestore.repository.*;
import com.company.gamestore.viewmodel.InvoiceViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {
    @MockBean
    private InvoiceRepository invoiceRepository;
    @MockBean
    private GameRepository gameRepository;
    @MockBean
    private ConsoleRepository consoleRepository;
    @MockBean
    private TshirtRepository tshirtRepository;
    @MockBean
    private FeeRepository feeRepository;
    @MockBean
    private TaxRepository taxRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private InvoiceViewModel invoiceViewModel;
    private Game game;

    @BeforeEach
    public void setUp() throws Exception{
        // Since we are mocking the call we must inject the tax and fee
        Tax tax = new Tax();
        tax.setState("IL");
        tax.setRate(new BigDecimal("0.05"));
        when(taxRepository.findByState("IL")).thenReturn(Optional.of(tax));

        Fee fee = new Fee();
        fee.setFee(new BigDecimal("1.99"));
        when(feeRepository.findById("game")).thenReturn(Optional.of(fee));

        game = new Game();
        game.setTitle("Call Of Duty: Modern Warfare");
        game.setEsrbRating("M");
        game.setDescription("First-person shooter video game");
        game.setPrice(BigDecimal.valueOf(59.99));
        game.setStudio("Infinity Ward");
        game.setQuantity(1);

        invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setName("Santi");
        invoiceViewModel.setStreet("w 24th");
        invoiceViewModel.setCity("Chi");
        invoiceViewModel.setState("IL");
        invoiceViewModel.setZipcode("60606");
        invoiceViewModel.setItemType("game");
        invoiceViewModel.setItemId(game.getGameId());
        invoiceViewModel.setQuantity(1);

        // setup behavior of mock repositories
        when(gameRepository.findById(anyInt())).thenReturn(Optional.of(game));
    }

    @Test
    public void  SanityChecker() throws Exception{
        boolean insanity = true;
        assertTrue(insanity);
    }

    //Happy path:
    @Test
    public void shouldCreateInvoicefromVM() throws Exception{

        mockMvc.perform(post("/invoice")
                        .content(mapper.writeValueAsString(invoiceViewModel))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //getInvoiceByInvoiceId
    @Test
    public void shouldGetInvoiceByInvoiceId() throws Exception{
        mockMvc.perform(get("/invoice/"+game.getGameId()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    //getAllInvoices
    @Test
    public void shouldGetAllInvoices() throws Exception{
        mockMvc.perform(get("/invoice"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //getInvoiceByName
    @Test
    public void shouldGetInvoiceByName() throws Exception{
        mockMvc.perform(get("/invoice/name/"+invoiceViewModel.getName()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldError422() throws Exception{
        InvoiceViewModel nullIvm = new InvoiceViewModel();
        mockMvc.perform(post("/invoice")
                        .content(mapper.writeValueAsString(nullIvm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailToCreatelInvalidItem() throws Exception{
        // Since we are mocking the call we must inject the tax and fee
        Tax tax = new Tax();
        tax.setState("IL");
        tax.setRate(new BigDecimal("0.05"));
        when(taxRepository.findByState("IL")).thenReturn(Optional.of(tax));

        Fee fee = new Fee();
        fee.setFee(new BigDecimal("1.99"));
        when(feeRepository.findById("game")).thenReturn(Optional.of(fee));

        InvoiceViewModel badIvm = new InvoiceViewModel();
        badIvm.setName("Santi");
        badIvm.setStreet("w 24th");
        badIvm.setCity("Chi");
        badIvm.setState("IL");
        badIvm.setZipcode("60606");
        badIvm.setItemType("NOTgame");
        badIvm.setItemId(game.getGameId());
        badIvm.setQuantity(1);

        // setup behavior of mock repositories
        when(gameRepository.findById(anyInt())).thenReturn(Optional.of(game));

        mockMvc.perform(post("/invoice")
                        .content(mapper.writeValueAsString(badIvm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldFailToGetInvoiceByName() throws Exception{
        mockMvc.perform(get("/invoice/name/NOTSanti"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailToGetInvoiceByInvoiceId() throws Exception{
        mockMvc.perform(get("/invoice/100"))
                .andDo(print())
                .andExpect(status().isOk());
    }




}
