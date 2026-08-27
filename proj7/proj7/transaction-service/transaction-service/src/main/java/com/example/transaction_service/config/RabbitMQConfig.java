package com.example.transaction_service.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "transaction_exchange";

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE);
    }
}