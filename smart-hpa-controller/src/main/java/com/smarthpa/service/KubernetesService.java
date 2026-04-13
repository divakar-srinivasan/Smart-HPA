package com.smarthpa.service;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KubernetesService {
    @Autowired
    private KubernetesClient kubernetesClient;

    public List<String> getDeploymentNames(String namespace) {
        // Discover deployments in the cluster
        return kubernetesClient.apps().deployments().inNamespace(namespace)
                .list().getItems().stream()
                .map(Deployment::getMetadata)
                .map(meta -> meta.getName())
                .collect(Collectors.toList());
    }
}
