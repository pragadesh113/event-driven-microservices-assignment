package com.example.transaction_service.controller;

import org.springframework.web.bind.annotation.*;

import com.example.transaction_service.model.Transaction;
import com.example.transaction_service.producer.TransactionProducer;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionProducer producer;

    public TransactionController(TransactionProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String create(@RequestBody Transaction transaction) {

        producer.send(transaction);

        return "TransactionCreated event published.";
    }

}