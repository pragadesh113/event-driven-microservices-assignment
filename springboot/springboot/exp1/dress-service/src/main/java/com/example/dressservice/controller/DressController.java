package com.example.dressservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dressservice.model.Dress;
import com.example.dressservice.service.DressService;

@RestController
@RequestMapping("/api")
public class DressController {

    private final DressService dressService;

    public DressController(DressService dressService) {
        this.dressService = dressService;
    }

    // SERVICE INFORMATION
    @GetMapping("/info")
    public String serviceInfo() {
        return "Online Food Delivery - Restaurant Service is running";
    }

    // GET ALL DRESSES
    @GetMapping("/dresses")
    public List<Dress> getAllDresses() {
        return dressService.getAllDresses();
    }

    // GET DRESS BY ID
    @GetMapping("/dresses/{id}")
    public Dress getDressById(@PathVariable int id) {
        return dressService.getDressById(id);
    }

    // ADD DRESS
    @PostMapping("/dresses")
    public Dress addDress(@RequestBody Dress dress) {
        return dressService.addDress(dress);
    }

    // UPDATE DRESS
    @PutMapping("/dresses/{id}")
    public Dress updateDress(
            @PathVariable int id,
            @RequestBody Dress dress) {

        return dressService.updateDress(id, dress);
    }

    // DELETE DRESS
    @DeleteMapping("/dresses/{id}")
    public String deleteDress(@PathVariable int id) {

        boolean deleted = dressService.deleteDress(id);

        if (deleted) {
            return "Dress deleted successfully";
        }

        return "Dress not found";
    }
}
