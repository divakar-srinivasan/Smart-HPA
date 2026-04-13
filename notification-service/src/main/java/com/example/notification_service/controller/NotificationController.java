package com.example.notification_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotificationController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Notification service is running";
    }
}

