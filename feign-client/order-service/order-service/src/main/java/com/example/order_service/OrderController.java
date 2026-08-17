package com.example.order_service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/order")
public class OrderController {

    @Autowired
    private PaymentClient paymentClient; // Inject the PaymentClient

    @GetMapping
    public String getOrder() {
        // Call the payment service
        String paymentResponse = paymentClient.processPayment();
        return "Order processed and " + paymentResponse; // Combine responses
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}