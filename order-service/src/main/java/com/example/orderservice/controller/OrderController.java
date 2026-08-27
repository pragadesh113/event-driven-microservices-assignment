package com.example.orderservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private String port;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public OrderController(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    @GetMapping("/service-info")
    public Map<String, Object> serviceInfo() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("service", serviceName);
        info.put("port", port);
        info.put("version", "1.0.0");
        info.put("description", "Order Service - manages orders");
        info.put("timestamp", LocalDateTime.now().toString());
        return info;
    }

    // Service-to-service communication using RestTemplate
    @GetMapping("/customer-info-resttemplate")
    public Map<String, Object> customerInfoViaRestTemplate() {
        String url = customerServiceUrl + "/customer/service-info";
        Object customerInfo = restTemplate.getForObject(url, Object.class);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("calledBy", serviceName);
        response.put("communicationMethod", "RestTemplate");
        response.put("customerServiceResponse", customerInfo);
        return response;
    }

    // Service-to-service communication using WebClient
    @GetMapping("/customer-info-webclient")
    public Map<String, Object> customerInfoViaWebClient() {
        String url = customerServiceUrl + "/customer/service-info";
        Object customerInfo = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("calledBy", serviceName);
        response.put("communicationMethod", "WebClient");
        response.put("customerServiceResponse", customerInfo);
        return response;
    }
}
