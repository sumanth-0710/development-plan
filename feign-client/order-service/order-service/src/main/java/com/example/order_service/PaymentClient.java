package com.example.order_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "payment-service") // This should match the application name in payment-service
public interface PaymentClient {
    @GetMapping("/api/payment/paymentprocessing")
    String processPayment();
}
