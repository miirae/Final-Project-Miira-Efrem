package com.company.gamestore.controller;

import com.company.gamestore.model.Game;
import com.company.gamestore.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {
    @MockBean
    private GameRepository gameRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private Game game;

    @BeforeEach
    public void setUp() throws Exception{
        game = new Game();
        game.setGameId(1);
        game.setTitle("Call Of Duty: Modern Warfare");
        game.setEsrbRating("M");
        game.setDescription("First-person shooter video game");
        game.setPrice(59.99);
        game.setStudio("Infinity Ward");
        game.setQuantity(1);
    }

    @Test
    void shouldAddGame() throws Exception{
        String inputJson = mapper.writeValueAsString(game);
        mockMvc.perform(post("/games")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void getGameById() throws Exception {
        mockMvc.perform(get("/games/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateGame() throws Exception {
        game.setEsrbRating("E10+");

        String inputJson = mapper.writeValueAsString(game);

        mockMvc.perform(put("/games")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteGameById() throws Exception {
        mockMvc.perform(delete("/games/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
