package com.company.gamestore.repository;


import com.company.gamestore.model.Console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ConsoleRepositoryTest {

    @Autowired
    ConsoleRepository repo;

    private Console console;

    @BeforeEach
    public void setUp() throws Exception{
        repo.deleteAll();
        console = new Console();
        console.setConsoleId(0);
        console.setModel("PS4");
        console.setManufacturer("Sony");
        console.setPrice(BigDecimal.valueOf(200.00));
        console.setQuantity(10);
        console = repo.save(console);
    }

    @Test
    public void shouldSaveNewConsole(){

        Optional<Console> existingConsole = repo.findById(console.getConsoleId());
        assertEquals(existingConsole.get(), console);
    }

    @Test
    public void shouldGetConsoleById() {
        Console existingConsole = repo.findById(console.getConsoleId()).orElse(null);
        assertEquals(console, existingConsole);
    }

    @Test
    public void shouldGetAllConsoles() {
        List<Console> consoles = repo.findAll();
        assertEquals(consoles.size(), 1);
    }

    @Test
    public void shouldUpdateConsole() {
        console.setPrice(BigDecimal.valueOf(500.00));
        repo.save(console);
        Optional<Console> existingConsole = repo.findById(console.getConsoleId());
        assertEquals(existingConsole.get(), console);
    }

    @Test
    public void shouldDeleteTshirtById(){
        repo.deleteById(console.getConsoleId());
        Optional<Console> newConsole = repo.findById(console.getConsoleId());
        assertFalse(newConsole.isPresent());
    }

    @Test
    public void getConsolesByManufacturer() {
        Console console1 = new Console();
        console1.setModel("xbox 360");
        console1.setManufacturer("Microsoft");
        console1.setPrice(BigDecimal.valueOf(500.00));
        console1.setQuantity(12);
        console1 = repo.save(console1);

        Console console2 = new Console();
        console2.setModel("xbox one");
        console2.setManufacturer("Microsoft");
        console2.setPrice(BigDecimal.valueOf(500.00));
        console2.setQuantity(1);
        console2 = repo.save(console2);

        List<Console> consoles = repo.findByManufacturer(console1.getManufacturer());
        assertTrue(consoles.size() == 2); // only xbox / microsoft should be returned
    }


}
