package com.company.gamestore.controller;

import com.company.gamestore.model.Tshirt;
import com.company.gamestore.repository.TshirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tshirts")
public class TshirtController {
    @Autowired
    TshirtRepository tshirtRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tshirt addTshirt(@RequestBody Tshirt tshirt) {
        return tshirtRepository.save(tshirt);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tshirt getTshirtById(@PathVariable int id) {
        Optional<Tshirt> tshirt = tshirtRepository.findById(id);
        return tshirt.orElse(null);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getAllTshirts() {
        return tshirtRepository.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTshirt(@RequestBody Tshirt tshirt, @PathVariable int id) {

        Optional<Tshirt> tshirt1 = tshirtRepository.findById(id);
        if(tshirt1.isPresent()){
            tshirtRepository.save(tshirt);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirt(@PathVariable int id) {
        tshirtRepository.deleteById(id);
    }

    @GetMapping("/tshirtcolor/{color}")
    public List<Tshirt> findByColor(@PathVariable String color) {
        return tshirtRepository.findByColor(color);
    }

    @GetMapping("/tshirtsize/{size}")
    public List<Tshirt> findBySize(@PathVariable String size) {
        return tshirtRepository.findBySize(size);
    }

}
