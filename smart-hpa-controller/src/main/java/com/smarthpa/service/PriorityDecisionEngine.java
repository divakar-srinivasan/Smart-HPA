package com.smarthpa.service;

import com.smarthpa.model.ServiceMetrics;
import com.smarthpa.model.ServicePriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PriorityDecisionEngine {
    private static final Logger logger = LoggerFactory.getLogger(PriorityDecisionEngine.class);

    public com.smarthpa.controller.SmartScalingController.ScalingReport applyPriorityScaling(
            List<ServiceMetrics> metrics, ScalingService scalingService, String namespace) {
        List<String> actions = new java.util.ArrayList<>();
        String status;
        // Find overloaded high-priority service
        ServiceMetrics highPriority = metrics.stream()
                .filter(m -> m.getPriority() == ServicePriority.HIGH && m.getCpuUsage() > 70)
                .findFirst().orElse(null);
        if (highPriority != null) {
            actions.add(String.format("High priority service %s is overloaded (CPU: %.2f%%)",
                    highPriority.getServiceName(), highPriority.getCpuUsage()));
            // Find lowest priority service
            ServiceMetrics lowPriority = metrics.stream()
                    .filter(m -> m.getPriority() == ServicePriority.LOW && m.getReplicas() > 1)
                    .min(Comparator.comparing(ServiceMetrics::getCpuUsage))
                    .orElse(null);
            if (lowPriority != null) {
                actions.add(String.format("Scaling down low priority service %s from %d to %d replicas",
                        lowPriority.getServiceName(), lowPriority.getReplicas(), lowPriority.getReplicas() - 1));
                scalingService.scaleDeployment(namespace, lowPriority.getServiceName(), lowPriority.getReplicas() - 1);
            }
            actions.add(String.format("Scaling up high priority service %s from %d to %d replicas",
                    highPriority.getServiceName(), highPriority.getReplicas(), highPriority.getReplicas() + 1));
            scalingService.scaleDeployment(namespace, highPriority.getServiceName(), highPriority.getReplicas() + 1);
            status = "Scaling actions performed.";
        } else {
            actions.add("No overloaded high priority service detected.");
            status = "No scaling required.";
        }
        return new com.smarthpa.controller.SmartScalingController.ScalingReport(metrics, actions, status);
    }
}
