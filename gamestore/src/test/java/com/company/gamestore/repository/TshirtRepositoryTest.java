package com.company.gamestore.repository;

import com.company.gamestore.model.Tshirt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TshirtRepositoryTest {

    @Autowired
    TshirtRepository tshirtRepository;

    private Tshirt tshirt;

    @BeforeEach
    public void setUp(){

        tshirtRepository.deleteAll();
        tshirt = new Tshirt();
        tshirt.setSize("S");
        tshirt.setColor("peach");
        tshirt.setDescription("made with 100% cotton");
        tshirt.setPrice(10.99);
        tshirt.setQuantity(1);
        tshirt = tshirtRepository.save(tshirt);
    }

    @Test
    public void shouldSaveNewTshirt(){

        Optional<Tshirt> existingTshirt = tshirtRepository.findById(tshirt.getTshirtId());
        assertEquals(existingTshirt.get(), tshirt);
    }

    @Test
    public void shouldGetTshirtById() {

        Tshirt existingTshirt = tshirtRepository.findById(tshirt.getTshirtId()).orElse(null);
        assertEquals(tshirt, existingTshirt);
    }

    @Test
    public void shouldGetAllTshirts() {

        List<Tshirt> tshirts = tshirtRepository.findAll();
        assertEquals(tshirts.size(), 1);
    }

    @Test
    public void shouldUpdateTshirt() {

        tshirt.setSize("L");
        tshirtRepository.save(tshirt);
        Optional<Tshirt> existingTshirt = tshirtRepository.findById(tshirt.getTshirtId());
        assertEquals(existingTshirt.get(), tshirt);
    }

    @Test
    public void shouldDeleteTshirtById() {

        tshirtRepository.deleteById(tshirt.getTshirtId());
        Optional<Tshirt> existingTshirt = tshirtRepository.findById(tshirt.getTshirtId());
        assertFalse(existingTshirt.isPresent());
    }

    @Test
    public void shouldGetTshirtsByColor() {

        Tshirt newTshirt = new Tshirt();
        newTshirt.setSize("M");
        newTshirt.setColor("Green");
        newTshirt.setDescription("made with 100% polyester");
        newTshirt.setPrice(15.99);
        newTshirt.setQuantity(2);
        tshirtRepository.save(newTshirt);

        List<Tshirt> tshirts = tshirtRepository.findByColor(tshirt.getColor());
        assertTrue(tshirts.contains(tshirt));
        assertFalse(tshirts.contains(newTshirt));
    }

    @Test
    public void shouldGetTshirtsBySize() {

        Tshirt newTshirt = new Tshirt();
        newTshirt.setSize("XL");
        newTshirt.setColor("Black");
        newTshirt.setDescription("made with 100% silk");
        newTshirt.setPrice(20.99);
        newTshirt.setQuantity(3);
        tshirtRepository.save(newTshirt);

        List<Tshirt> tshirts = tshirtRepository.findBySize(tshirt.getSize());
        assertTrue(tshirts.contains(tshirt));
        assertFalse(tshirts.contains(newTshirt));
    }
}
