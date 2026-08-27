package com.example.contexts.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.contexts.delivery.DeliveryContextService;
import com.example.contexts.order.OrderContextService;
import com.example.contexts.payment.PaymentContextService;

@RestController
@RequestMapping("/contexts")
public class ContextDemoController {
    private final OrderContextService orders;
    private final PaymentContextService payments;
    private final DeliveryContextService deliveries;

    public ContextDemoController(OrderContextService orders, PaymentContextService payments,
                                 DeliveryContextService deliveries) {
        this.orders = orders;
        this.payments = payments;
        this.deliveries = deliveries;
    }

    @PostMapping("/demo")
    public Map<String, String> demo(@RequestParam String orderId, @RequestParam double amount) {
        orders.place(orderId, amount);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("orderContext", orders.status(orderId));
        result.put("paymentContext", payments.status(orderId));
        result.put("deliveryContext", deliveries.status(orderId));
        return result;
    }
}
