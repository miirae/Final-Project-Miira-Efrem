package com.company.gamestore.controller;

import com.company.gamestore.model.Tax;
import com.company.gamestore.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class TaxController {

    @Autowired
    TaxRepository taxRepository;

    @PostMapping("/tax")
    @ResponseStatus(HttpStatus.CREATED)
    public Tax createTax(@RequestBody @Valid Tax tax){
        return taxRepository.save(tax);
    }

    @GetMapping(path = "/tax/{state}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Tax> getTaxbyState(@PathVariable String state){
        return taxRepository.findById(state);
    }

    @PutMapping("/tax")
    @ResponseStatus(HttpStatus.OK)
    public Tax updateTax(@RequestBody @Valid Tax tax){
        return taxRepository.save(tax);
    }

    @DeleteMapping("/tax/{state}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTax(@PathVariable String state){
        taxRepository.deleteById(state);
    }

}
