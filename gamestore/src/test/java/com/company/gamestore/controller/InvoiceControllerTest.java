package com.company.gamestore.controller;

import com.company.gamestore.model.Console;
import com.company.gamestore.model.Game;
import com.company.gamestore.model.Tshirt;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        game = new Game();
        game.setTitle("Call Of Duty: Modern Warfare");
        game.setEsrbRating("M");
        game.setDescription("First-person shooter video game");
        game.setPrice(BigDecimal.valueOf(59.99));
        game.setStudio("Infinity Ward");
        game.setQuantity(1);
        game = gameRepository.save(game);

        invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setName("Santi");
        invoiceViewModel.setStreet("w 24th");
        invoiceViewModel.setCity("Chi");
        invoiceViewModel.setState("IL");
        invoiceViewModel.setZipcode("60606");
        invoiceViewModel.setItemType("game");
        invoiceViewModel.setItemId(game.getGameId());
        invoiceViewModel.setQuantity(game.getQuantity());
    }

    @Test
    public void  SanityChecker() throws Exception{
        boolean insanity = true;
        assertTrue(insanity);
    }

    @Test
    public void createInvoicefromVM() throws Exception{
        mockMvc.perform(post("/invoice")
                        .content(mapper.writeValueAsString(invoiceViewModel))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //TODO:getInvoiceByInvoiceId
    //TODO:getAllInvoices
    //TODO:getInvoiceByName


}
