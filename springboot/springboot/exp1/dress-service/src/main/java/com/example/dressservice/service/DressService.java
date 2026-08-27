package com.example.dressservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dressservice.model.Dress;

@Service
public class DressService {

    private final List<Dress> dresses = new ArrayList<>();

    public DressService() {
        dresses.add(new Dress(1, "Floral Summer Dress",
                "Casual", 1299.00, "M"));

        dresses.add(new Dress(2, "Black Evening Gown",
                "Party", 2499.00, "L"));
    }

    // READ ALL
    public List<Dress> getAllDresses() {
        return dresses;
    }

    // READ ONE
    public Dress getDressById(int id) {
        for (Dress dress : dresses) {
            if (dress.getId() == id) {
                return dress;
            }
        }
        return null;
    }

    // CREATE
    public Dress addDress(Dress dress) {
        dresses.add(dress);
        return dress;
    }

    // UPDATE
    public Dress updateDress(int id, Dress updatedDress) {

        for (Dress dress : dresses) {

            if (dress.getId() == id) {

                dress.setName(updatedDress.getName());
                dress.setCategory(updatedDress.getCategory());
                dress.setPrice(updatedDress.getPrice());
                dress.setSize(updatedDress.getSize());

                return dress;
            }
        }

        return null;
    }

    // DELETE
    public boolean deleteDress(int id) {

        return dresses.removeIf(dress -> dress.getId() == id);
    }
}