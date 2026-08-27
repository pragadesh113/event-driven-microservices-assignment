package com.example.notification_service.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = "notification_queue")
    public void receive(Message message) {

        System.out.println("========== NOTIFICATION SERVICE ==========");

        System.out.println(new String(message.getBody()));

        System.out.println("Notification Sent Successfully");

        System.out.println("==========================================");

    }

}