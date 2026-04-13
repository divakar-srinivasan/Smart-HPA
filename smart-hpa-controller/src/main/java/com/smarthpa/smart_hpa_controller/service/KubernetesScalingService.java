package com.smarthpa.smart_hpa_controller.service;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class KubernetesScalingService {

    public void scaleDeployment(String deploymentName, int replicas) {

        try (KubernetesClient client = new KubernetesClientBuilder().build()) {

            client.apps()
                    .deployments()
                    .inNamespace("default")
                    .withName(deploymentName)
                    .scale(replicas);

            System.out.println("Scaled " + deploymentName + " to " + replicas);

        } catch (Exception e) {

            System.out.println("Scaling failed: " + e.getMessage());

        }
    }
}