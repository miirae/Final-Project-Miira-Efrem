package com.company.gamestore;

import com.company.gamestore.model.Game;
import com.company.gamestore.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    private Game game;

    @BeforeEach
    public void setUp() {
        gameRepository.deleteAll();
        game = new Game();
        game.setTitle("Call Of Duty: Modern Warfare");
        game.setEsrbRating("M");
        game.setDescription("First-person shooter video game");
        game.setPrice(59.99);
        game.setStudio("Infinity Ward");
        game.setQuantity(1);
        game = gameRepository.save(game);
    }

    @Test
    public void shouldAddGame() {
        Optional<Game> actual = gameRepository.findById(game.getGameId());
        assertEquals(actual.get(), game);
    }

    @Test
    public void shouldGetAllGames() {
        List<Game> games = gameRepository.findAll();
        assertEquals(games.size(), 1);
    }

    @Test
    public void shouldGetGameById() {
        Optional<Game> actual = gameRepository.findById(game.getGameId());

        assertEquals(game, actual.get());
    }

    @Test
    public void shouldUpdateGame() {
        game.setPrice(29.99);
        Game updatedGame = gameRepository.save(game);
        assertEquals(game, updatedGame);
    }

    @Test
    public void shouldDeleteGameById() {
        gameRepository.deleteById(game.getGameId());
        Optional<Game> actual = gameRepository.findById(game.getGameId());
        assertFalse(actual.isPresent());
    }

    @Test
    public void shouldGetGameByStudio() {
        Game newGame = new Game();
        newGame.setTitle("Minecraft");
        newGame.setEsrbRating("E10+");
        newGame.setDescription("Creation game that allows users to build anything or learn to survive in pre-generated worlds");
        newGame.setPrice(19.99);
        newGame.setStudio("Mojang");
        newGame.setQuantity(1);
        gameRepository.save(newGame);

        List<Game> games = gameRepository.findByStudio(game.getStudio());
        assertTrue(games.contains(game));
        assertFalse(games.contains(newGame));
    }

    @Test
    public void shouldGetGameByRating() {
        Game newGame = new Game();
        newGame.setTitle("Grand Theft Auto V");
        newGame.setEsrbRating("M");
        newGame.setDescription("Action-adventure game played from either a third-person or first-person perspective");
        newGame.setPrice(39.99);
        newGame.setStudio("Rockstar North");
        newGame.setQuantity(1);
        gameRepository.save(newGame);

        List<Game> games = gameRepository.findByEsrbRating(game.getEsrbRating());
        assertTrue(games.contains(game));
        assertTrue(games.contains(newGame));
    }

    @Test
    public void shouldGetGameByTitle() {
        Game newGame = new Game();
        newGame.setTitle("Grand Theft Auto V");
        newGame.setEsrbRating("M");
        newGame.setDescription("Action-adventure game played from either a third-person or first-person perspective");
        newGame.setPrice(39.99);
        newGame.setStudio("Rockstar North");
        newGame.setQuantity(1);
        gameRepository.save(newGame);

        List<Game> games = gameRepository.findByTitle(game.getTitle());
        assertTrue(games.contains(game));
        assertFalse(games.contains(newGame));
    }
}
