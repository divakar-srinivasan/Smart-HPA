package com.example.payment_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentLoadController {
        @GetMapping("/health")
    public String health() {
        return "Payment Service Running";
    }

    @GetMapping("/load")
    public String processPayment() {

        long endTime = System.currentTimeMillis() + 3000;

        while (System.currentTimeMillis() < endTime) {
            Math.pow(Math.random(), Math.random());
        }

        return "Payment processed";
    }
}
