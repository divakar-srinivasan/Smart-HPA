package com.smarthpa.controller;

import com.smarthpa.service.MetricsService;
import com.smarthpa.service.PriorityDecisionEngine;
import com.smarthpa.service.ScalingService;
import com.smarthpa.model.ServiceMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SmartScalingController {
    @Autowired
    private ScalingService scalingService;
    @Autowired
    private MetricsService metricsService;
    @Autowired
    private PriorityDecisionEngine decisionEngine;

    private static final Logger logger = LoggerFactory.getLogger(SmartScalingController.class);
    private static final String NAMESPACE = "default";

    // Manual scaling endpoint
    @PostMapping("scale/{deployment}/{replicas}")
    public String scaleDeployment(@PathVariable String deployment, @PathVariable int replicas) {
        scalingService.scaleDeployment(NAMESPACE, deployment, replicas);
        logger.info("Manually scaled {} to {} replicas", deployment, replicas);
        return String.format("Scaled %s to %d replicas", deployment, replicas);
    }

    // Smart scaling endpoint
    @PostMapping("smart-scale")
    public Object smartScale() {
        List<ServiceMetrics> metrics = metricsService.getServiceMetrics(NAMESPACE);
        ScalingReport report = decisionEngine.applyPriorityScaling(metrics, scalingService, NAMESPACE);
        logger.info("Smart scaling triggered via REST endpoint");
        return report;
    }

    // Scaling report DTO
    public static class ScalingReport {
        public List<ServiceMetrics> metrics;
        public List<String> actions;
        public String status;

        public ScalingReport(List<ServiceMetrics> metrics, List<String> actions, String status) {
            this.metrics = metrics;
            this.actions = actions;
            this.status = status;
        }
    }
}
