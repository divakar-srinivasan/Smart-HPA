package com.example.order_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class LoadController {
    @GetMapping("/health")
    public String health() {
        return "Order Service Running";
    }

    @GetMapping("/load")
    public String generateLoad() {

        long endTime = System.currentTimeMillis() + 3000;

        while (System.currentTimeMillis() < endTime) {
            Math.sqrt(Math.random());
        }

        return "CPU load generated";
    }
}
