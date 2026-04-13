package com.smarthpa.config;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KubernetesClientConfig {
    @Bean
    public KubernetesClient kubernetesClient() {
        // Initializes Fabric8 Kubernetes Client using default kubeconfig
        return new KubernetesClientBuilder().build();
    }
}
