package com.company.gamestore.controller;


import com.company.gamestore.model.Console;
import com.company.gamestore.model.Tshirt;
import com.company.gamestore.repository.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consoles")
public class ConsoleController {

    @Autowired
    ConsoleRepository consoleRepository;

    // create console
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Console addConsole(@RequestBody @Valid Console console){
        return consoleRepository.save(console);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Console getConsole(@PathVariable int id) {
        Optional<Console> console = consoleRepository.findById(id);
        return console.orElse(null);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Console> getAllConsoles() {
        return consoleRepository.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConsole(@RequestBody @Valid Console console, @PathVariable int id) {
        Optional<Console> console1 = consoleRepository.findById(id); // c1 is what i get from database
        if (console1.isPresent()) {
            console.setConsoleId(console1.get().getConsoleId());
            consoleRepository.save(console);
        }
    }

    // delete console information
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsole(@PathVariable int id) {
        Optional<Console> console1 = consoleRepository.findById(id);
        if (console1.isPresent()) {
            consoleRepository.deleteById(id);
        }
    }

    // search for consoles by manufacturer
    @GetMapping("/manufacturer/{manName}")
    public List<Console> findByManufacturer(@PathVariable String manName){
        List<Console> consolesByManName = consoleRepository.findByManufacturer(manName);
        return consolesByManName;
    }
}
