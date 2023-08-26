package com.company.gamestore.repository;

import com.company.gamestore.model.Fee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FeeRepositoryTest {

    @Autowired
    FeeRepository feeRepository;

    @BeforeEach
    public void setUp() throws Exception {
        feeRepository.deleteAll();
        feeRepository.save(new Fee("NA", BigDecimal.valueOf(0.01)));
        feeRepository.save(new Fee("AN", BigDecimal.valueOf(0.21)));
    }

    @Test
    public void addTax() {
        Optional<Fee> newFee = feeRepository.findByProductType("NA");
        Fee controlTax = new Fee();
        controlTax.setProductType("NA");
        controlTax.setFee(BigDecimal.valueOf(0.01));
        assertEquals(controlTax, newFee.get());
    }

    @Test
    public void getAllTaxes() {
        assertEquals(2, feeRepository.findAll().size());
    }

    @Test
    public void updateTax() {
        Fee newTax = new Fee("NA", BigDecimal.valueOf(0.99));
        feeRepository.save(newTax);
        Optional<Fee> foundTax = feeRepository.findByProductType("NA");
        assertTrue(foundTax.isPresent());
        assertEquals(BigDecimal.valueOf(0.99), foundTax.get().getFee());
    }

}
