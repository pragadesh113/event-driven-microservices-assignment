package com.example.orderworkerservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.orderworkerservice.config.RabbitMQConfig;
import com.example.orderworkerservice.event.OrderCreatedEvent;

@Component
public class OrderProcessingConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void workerOne(OrderCreatedEvent event) {
        print("Worker-1", event);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void workerTwo(OrderCreatedEvent event) {
        print("Worker-2", event);
    }

    private void print(String worker, OrderCreatedEvent event) {
        System.out.println(worker + " processed order " + event.getOrderId()
                + " - " + event.getProduct() + " x " + event.getQuantity());
    }
}
