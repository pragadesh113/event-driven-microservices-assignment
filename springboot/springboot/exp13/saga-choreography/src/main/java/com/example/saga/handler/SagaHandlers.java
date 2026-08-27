package com.example.saga.handler;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.saga.event.SagaEvents.DeliveryAssigned;
import com.example.saga.event.SagaEvents.DeliveryFailed;
import com.example.saga.event.SagaEvents.OrderCreated;
import com.example.saga.event.SagaEvents.PaymentCompleted;
import com.example.saga.event.SagaEvents.PaymentFailed;
import com.example.saga.service.SagaStateStore;

@Component
public class SagaHandlers {
    private final ApplicationEventPublisher publisher;
    private final SagaStateStore states;

    public SagaHandlers(ApplicationEventPublisher publisher, SagaStateStore states) {
        this.publisher = publisher;
        this.states = states;
    }

    @EventListener
    public void takePayment(OrderCreated event) {
        states.set(event.orderId(), "PAYMENT_PROCESSING");
        if (event.amount() <= 0) {
            publisher.publishEvent(new PaymentFailed(event.orderId(), "Invalid amount"));
        } else {
            publisher.publishEvent(new PaymentCompleted(event.orderId(), event.deliveryAvailable()));
        }
    }

    @EventListener
    public void arrangeDelivery(PaymentCompleted event) {
        states.set(event.orderId(), "PAID");
        if (event.deliveryAvailable()) {
            publisher.publishEvent(new DeliveryAssigned(event.orderId(), "Partner-01"));
        } else {
            publisher.publishEvent(new DeliveryFailed(event.orderId(), "No delivery partner"));
        }
    }

    @EventListener
    public void paymentFailed(PaymentFailed event) {
        states.set(event.orderId(), "CANCELLED_PAYMENT_FAILED");
    }

    @EventListener
    public void deliveryAssigned(DeliveryAssigned event) {
        states.set(event.orderId(), "DELIVERY_ASSIGNED:" + event.deliveryPartner());
    }

    @EventListener
    public void deliveryFailed(DeliveryFailed event) {
        states.set(event.orderId(), "REFUND_REQUIRED_DELIVERY_FAILED");
    }
}
