package com.example.analytics_service.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsConsumer {

    @RabbitListener(queues = "analytics_queue")
    public void receive(Message message) {

        System.out.println("========== ANALYTICS SERVICE ==========");

        System.out.println(new String(message.getBody()));

        System.out.println("Analytics Updated Successfully");

        System.out.println("=======================================");

    }
}