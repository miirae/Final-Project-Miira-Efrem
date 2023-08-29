package com.company.gamestore.controller;

import com.company.gamestore.model.Tshirt;
import com.company.gamestore.repository.TshirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tshirts")
public class TshirtController {
    @Autowired
    TshirtRepository tshirtRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tshirt addTshirt(@RequestBody @Valid Tshirt tshirt) {
        return tshirtRepository.save(tshirt);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tshirt getTshirtById(@PathVariable int id) {
        Optional<Tshirt> existingTshirt = tshirtRepository.findById(id);
        if (existingTshirt.isPresent()) {
            return existingTshirt.get();
        } else {
            throw new NotFoundException("No t-shirt with the given id found");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tshirt> getAllTshirts() {
        List<Tshirt> tshirts = tshirtRepository.findAll();
        if (tshirts.isEmpty()) {
            throw new NotFoundException("No t-shirts found");
        }
        return tshirts;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Tshirt updateTshirt(@RequestBody @Valid Tshirt tshirt) {
        Optional<Tshirt> existingTshirt = tshirtRepository.findById(tshirt.getTshirtId());
        if (existingTshirt.isPresent()) {
            return tshirtRepository.save(tshirt);
        } else {
            throw new NotFoundException("No t-shirt with the given id found");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTshirt(@PathVariable int id) {
        Optional<Tshirt> tshirt1 = tshirtRepository.findById(id);
        if (tshirt1.isPresent()) {
            tshirtRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("No t-shirt with the given id found");
        }
    }

    @GetMapping("/tshirtcolor/{color}")
    public List<Tshirt> findByColor(@PathVariable String color) {
        List<Tshirt> tshirts = tshirtRepository.findByColor(color);
        if (tshirts.isEmpty()) {
            throw new NotFoundException("No t-shirt with the given color found");
        }
        return tshirts;
    }

    @GetMapping("/tshirtsize/{size}")
    public List<Tshirt> findBySize(@PathVariable String size) {
        List<Tshirt> tshirts = tshirtRepository.findBySize(size);
        if(tshirts.isEmpty()){
            throw new NotFoundException("No t-shirt with the given size found");
        }
        return tshirts;
    }
}
