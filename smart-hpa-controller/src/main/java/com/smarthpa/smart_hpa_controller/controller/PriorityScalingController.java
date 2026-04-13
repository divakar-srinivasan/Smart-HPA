package com.smarthpa.smart_hpa_controller.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarthpa.smart_hpa_controller.service.KubernetesScalingService;

@RestController
public class PriorityScalingController {

    private final KubernetesScalingService scalingService;

    public PriorityScalingController(KubernetesScalingService scalingService) {
        this.scalingService = scalingService;
    }

    @GetMapping("/smart-scale")
    public String smartScale() {

        // Example priority scaling
        scalingService.scaleDeployment("notification-service", 0);
        scalingService.scaleDeployment("order-service", 3);

        return "Priority scaling executed";
    }
}