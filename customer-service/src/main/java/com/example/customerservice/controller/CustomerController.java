package com.example.customerservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private String port;

    @GetMapping("/service-info")
    public Map<String, Object> serviceInfo() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("service", serviceName);
        info.put("port", port);
        info.put("version", "1.0.0");
        info.put("description", "Customer Service - manages customer information");
        info.put("timestamp", LocalDateTime.now().toString());
        return info;
    }
}
