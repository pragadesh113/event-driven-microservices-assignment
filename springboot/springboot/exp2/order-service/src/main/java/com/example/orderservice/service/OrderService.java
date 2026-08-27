package com.example.orderservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.orderservice.model.Order;

@Service
public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    public OrderService() {

        orders.add(new Order(
                1,
                1,
                "Veg Pizza",
                299.00,
                "Confirmed"
        ));

        orders.add(new Order(
                2,
                2,
                "Paneer Biryani",
                249.00,
                "Shipped"
        ));
    }

    public List<Order> getAllOrders() {

        return orders;
    }

    public Order getOrderById(int id) {

        for (Order order : orders) {

            if (order.getId() == id) {
                return order;
            }
        }

        return null;
    }

    public Order addOrder(Order order) {

        orders.add(order);
        return order;
    }

    public Order updateOrder(int id, Order updatedOrder) {

        for (Order order : orders) {

            if (order.getId() == id) {

                order.setCustomerId(updatedOrder.getCustomerId());
                order.setItemName(updatedOrder.getItemName());
                order.setAmount(updatedOrder.getAmount());
                order.setStatus(updatedOrder.getStatus());

                return order;
            }
        }

        return null;
    }

    public boolean deleteOrder(int id) {

        return orders.removeIf(
                order -> order.getId() == id
        );
    }
}
