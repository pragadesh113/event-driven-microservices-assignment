package com.example.transaction_service.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.transaction_service.config.RabbitMQConfig;
import com.example.transaction_service.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public TransactionProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Transaction transaction) {

        try {
            String json = mapper.writeValueAsString(transaction);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    "",
                    json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}