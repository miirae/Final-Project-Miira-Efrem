package com.company.gamestore.controller;

import com.company.gamestore.model.Fee;
import com.company.gamestore.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class FeeController {

    @Autowired
    FeeRepository feeRepository;

    @PostMapping("/fee")
    @ResponseStatus(HttpStatus.CREATED)
    public Fee createFee(@RequestBody @Valid Fee fee){
        return feeRepository.save(fee);
    }

    @GetMapping("/fee/{productType}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Fee> getFeeByProductType(@PathVariable String productType) {
        return feeRepository.findById(productType);
    }

    @PutMapping("/fee")
    @ResponseStatus(HttpStatus.OK)
    public Fee updateFee(@RequestBody @Valid Fee fee) {
        return feeRepository.save(fee);
    }

    @DeleteMapping("/fee/{productType}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFee(@PathVariable String productType) {
        feeRepository.deleteById(productType);
    }

}
