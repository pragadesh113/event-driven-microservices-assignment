package com.example.idempotent.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idempotent.event.OrderEvent;
import com.example.idempotent.service.IdempotentEventProcessor;
import com.example.idempotent.service.IdempotentEventProcessor.Result;

@RestController
@RequestMapping("/events")
public class EventController {
    private final IdempotentEventProcessor processor;

    public EventController(IdempotentEventProcessor processor) {
        this.processor = processor;
    }

    @PostMapping
    public Result receive(@RequestBody OrderEvent event) {
        return processor.process(event);
    }
}
