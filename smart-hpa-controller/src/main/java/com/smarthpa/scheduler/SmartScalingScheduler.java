package com.smarthpa.scheduler;

import com.smarthpa.service.MetricsService;
import com.smarthpa.service.PriorityDecisionEngine;
import com.smarthpa.service.ScalingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class SmartScalingScheduler {
    @Autowired
    private MetricsService metricsService;
    @Autowired
    private PriorityDecisionEngine decisionEngine;
    @Autowired
    private ScalingService scalingService;

    private static final Logger logger = LoggerFactory.getLogger(SmartScalingScheduler.class);
    private static final String NAMESPACE = "default";

    /**
     * Scheduler runs every 10 seconds and automatically monitors the Kubernetes
     * cluster.
     * It collects CPU metrics, evaluates priority-aware scaling logic, and applies
     * scaling actions.
     * Logs each step for visibility.
     */
    @Scheduled(fixedRate = 10000)
    public void checkAndScale() {
        logger.info("Smart HPA monitor triggered");
        logger.info("Evaluating cluster state");
        var metrics = metricsService.getServiceMetrics(NAMESPACE);
        logger.info("Checking cluster metrics...");
        boolean resourceExhausted = metrics.stream().anyMatch(s -> s.getCpuUsage() > 90);
        if (resourceExhausted) {
            logger.warn(
                    "ALERT: Cluster resources are nearing exhaustion! One or more services have CPU usage above 90%.");
        }
        logger.info("Evaluating service priorities...");
        var report = decisionEngine.applyPriorityScaling(metrics, scalingService, NAMESPACE);
        for (String action : report.actions) {
            logger.info(action);
        }
        logger.info("Scaling decision applied...");
    }
}
