package com.smarthpa.service;

import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScalingService {
    @Autowired
    private KubernetesClient kubernetesClient;

    private static final Logger logger = LoggerFactory.getLogger(ScalingService.class);

    public void scaleDeployment(String namespace, String deploymentName, int replicas) {
        // Scale deployment using Fabric8 client
        logger.info("Scaling deployment {} to {} replicas", deploymentName, replicas);
        kubernetesClient.apps()
                .deployments()
                .inNamespace(namespace)
                .withName(deploymentName)
                .scale(replicas);
    }
}
