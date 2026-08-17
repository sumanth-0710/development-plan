package com.example.payment_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @GetMapping("/paymentprocessing")
    public String getPayment() {
        return "Payment processed";
    }
}
