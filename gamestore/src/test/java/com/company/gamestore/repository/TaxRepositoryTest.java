package com.company.gamestore.repository;

import com.company.gamestore.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TaxRepositoryTest {

    @Autowired
    TaxRepository taxRepository;

    @BeforeEach
    public void setUp() throws Exception {
        taxRepository.deleteAll();
        taxRepository.save(new Tax("NA", BigDecimal.valueOf(0.01)));
        taxRepository.save(new Tax("AN", BigDecimal.valueOf(0.21)));
    }

    @Test
    public void addTax() {
        Optional<Tax> newTax = taxRepository.findByState("NA");
        Tax controlTax = new Tax();
        controlTax.setState("NA");
        controlTax.setRate(BigDecimal.valueOf(0.01));
        assertEquals(controlTax, newTax.get());
    }

    @Test
    public void getAllTaxes() {
        assertEquals(2, taxRepository.findAll().size());
    }

    @Test
    public void updateTax() {
        Tax newTax = new Tax("NA", BigDecimal.valueOf(0.99));
        taxRepository.save(newTax);
        Optional<Tax> foundTax = taxRepository.findByState("NA");
        assertTrue(foundTax.isPresent());
        assertEquals(BigDecimal.valueOf(0.99), foundTax.get().getRate());
    }

}
