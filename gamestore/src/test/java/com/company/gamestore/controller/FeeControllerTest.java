package com.company.gamestore.controller;

import com.company.gamestore.model.Fee;
import com.company.gamestore.repository.FeeRepository;
import com.company.gamestore.repository.TaxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FeeController.class)
public class FeeControllerTest {

    @MockBean
    private FeeRepository feeRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private Fee fee;

    @BeforeEach
    public void setUp() throws Exception{
        fee = new Fee("test", BigDecimal.valueOf(1.11));
    }

    @Test
    void shouldCreateFee() throws Exception{
        String inputJson = mapper.writeValueAsString(fee);
        mockMvc.perform(post("/fee")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getFeeByProductType() throws Exception {
        mockMvc.perform(get("/fee/test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateFee() throws Exception {
        fee.setFee(BigDecimal.valueOf(1.11));

        String inputJson = mapper.writeValueAsString(fee);

        mockMvc.perform(put("/fee")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteFeeByProductType() throws Exception {
        mockMvc.perform(delete("/fee/test"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
