package com.company.gamestore.controller;

import com.company.gamestore.model.Console;
import com.company.gamestore.repository.ConsoleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest {

    @MockBean
    private ConsoleRepository consoleRepo;

    @Autowired
    private MockMvc mockmvc;

    private ObjectMapper mapper = new ObjectMapper();

    private Console console;

    @BeforeEach
    public void setUp() throws Exception{
        console = new Console();
        console.setConsoleId(0);
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setPrice(200.00);
        console.setQuantity(10);
    }

    @Test
    void getById() throws Exception{
        String inputJson = mapper.writeValueAsString(console);
        mockmvc.perform(get("/consoles/"+console.getConsoleId())
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllConsoles() throws Exception{
        // Act & Assert
        mockmvc.perform(get("/consoles/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateConsole() throws Exception {
        String inputJson = mapper.writeValueAsString(console);
        mockmvc.perform(post("/consoles")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getProductByManufacturer() throws Exception {
        mockmvc.perform(get("/consoles/manufacturer/Sony"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateConsole() throws Exception {
        console.setPrice(450);
        String inputJson = mapper.writeValueAsString(console);
        mockmvc.perform(put("/consoles/" + console.getConsoleId())
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteConsoleById() throws Exception {
        mockmvc.perform(delete("/consoles/" +console.getConsoleId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
