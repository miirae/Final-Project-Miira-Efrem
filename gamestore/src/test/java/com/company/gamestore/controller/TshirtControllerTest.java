package com.company.gamestore.controller;

import com.company.gamestore.model.Tshirt;
import com.company.gamestore.repository.TshirtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TshirtController.class)
public class TshirtControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TshirtRepository tshirtRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    private Tshirt tshirt;

    @BeforeEach
    public void setUp(){
        tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("peach");
        tshirt.setDescription("made with 100% cotton");
        tshirt.setPrice(10.99);
        tshirt.setQuantity(1);
    }

    @Test
    public void addTshirtTest() throws Exception{
        when(tshirtRepository.save(any(Tshirt.class))).thenReturn(tshirt);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/tshirts")
                        .content(mapper.writeValueAsString(tshirt))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

    @Test
    public void getTshirtByIdTest() throws Exception {
        when(tshirtRepository.findById(1)).thenReturn(Optional.of(tshirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/{id}", 1))
                        .andExpect(status().isOk());
    }

    @Test
    public void getAllTshirtTest() throws Exception {

        when(tshirtRepository.findAll()).thenReturn(Collections.singletonList(tshirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void updateTshirtTest() throws Exception {
        when(tshirtRepository.findById(anyInt())).thenReturn(Optional.of(tshirt));
        when(tshirtRepository.save(any(Tshirt.class))).thenReturn(tshirt);

        tshirt.setSize("M");
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/tshirts")
                        .content(mapper.writeValueAsString(tshirt))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void deleteTshirtTest() throws Exception {
        when(tshirtRepository.findById(1)).thenReturn(Optional.of(tshirt));
        doNothing().when(tshirtRepository).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/tshirts/{id}", 1))
                        .andExpect(status().isNoContent());
    }

    @Test
    public void getTshirtByColorTest() throws Exception {
        when(tshirtRepository.findByColor("peach")).thenReturn(Collections.singletonList(tshirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/tshirtcolor/{color}", "peach"))
                        .andExpect(status().isOk());
    }

    @Test
    public void getTshirtBySizeTest() throws Exception {
        when(tshirtRepository.findBySize("S")).thenReturn(Collections.singletonList(tshirt));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/tshirtsize/{size}", "S"))
                .andExpect(status().isOk());
    }

    @Test
    public void addNegativeTshirtPriceTest() throws Exception {

        Tshirt negativeTshirtPrice = new Tshirt();
        negativeTshirtPrice.setSize("L");
        negativeTshirtPrice.setColor("Black");
        negativeTshirtPrice.setDescription("made with 100% polyester");
        negativeTshirtPrice.setPrice(-10.99);
        negativeTshirtPrice.setQuantity(2);

        tshirtRepository.save(negativeTshirtPrice);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/tshirts")
                        .content(mapper.writeValueAsString(negativeTshirtPrice))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void getAllTshirtsNotFoundTest() throws Exception {
        when(tshirtRepository.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByColorNotFoundTest() throws Exception {
        when(tshirtRepository.findByColor("blue")).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/tshirtcolor/{color}", "blue"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findBySizeNotFoundTest() throws Exception {
        when(tshirtRepository.findByColor("XXL")).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tshirts/tshirtsize/{size}", "XXL"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTshirtNotFoundTest() throws Exception {
        when(tshirtRepository.save(any(Tshirt.class))).thenReturn(tshirt);
        
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/tshirts")
                        .content(mapper.writeValueAsString(tshirt))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteTshirtNotFoundTest() throws Exception {
        doNothing().when(tshirtRepository).deleteById(1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/tshirts/{id}", 1))
                .andExpect(status().isNotFound());
    }
}