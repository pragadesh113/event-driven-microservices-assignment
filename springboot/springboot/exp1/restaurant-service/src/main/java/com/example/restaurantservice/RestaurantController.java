package com.example.restaurantservice;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    @GetMapping("/info")
    public Map<String, String> serviceInfo() {
        return Map.of(
                "service", "Restaurant Service",
                "status", "RUNNING",
                "description", "Provides restaurant and menu information"
        );
    }
}
