package com.company.gamestore.controller;

import com.company.gamestore.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Table;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void  SanityChecker() throws Exception{
        boolean insanity = true;
        assertTrue(insanity);
    }

}
