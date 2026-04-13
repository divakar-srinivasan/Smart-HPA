package com.smarthpa.service;

import com.smarthpa.model.ServiceMetrics;
import com.smarthpa.model.ServicePriority;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsService {
    @Autowired
    private KubernetesClient kubernetesClient;

    private static final Logger logger = LoggerFactory.getLogger(MetricsService.class);

    public List<ServiceMetrics> getServiceMetrics(String namespace) {
        List<ServiceMetrics> metricsList = new ArrayList<>();
        List<Deployment> deployments = kubernetesClient.apps().deployments().inNamespace(namespace).list().getItems();
        for (Deployment deployment : deployments) {
            String name = deployment.getMetadata().getName();
            int replicas = deployment.getSpec().getReplicas();
            double cpuUsage = getCpuUsageForDeployment(name, namespace);
            ServicePriority priority = ServicePriority.fromServiceName(name);
            metricsList.add(new ServiceMetrics(name, cpuUsage, replicas, priority));
        }
        return metricsList;
    }

    private double getCpuUsageForDeployment(String deploymentName, String namespace) {
        // Read pod CPU metrics using Kubernetes Metrics API
        // This is a placeholder. Fabric8 supports metrics if Metrics Server is
        // installed.
        // For real implementation, query metrics.k8s.io API for pod CPU usage.
        try {
            // Example: return kubernetesClient.top().pods().metrics(namespace,
            // deploymentName).getUsage().get("cpu");
            // For demo, return random value
            return Math.random() * 100;
        } catch (Exception e) {
            logger.warn("Failed to get CPU metrics for {}: {}", deploymentName, e.getMessage());
            return 0.0;
        }
    }
}
