package com.smarthpa.model;

public class ServiceMetrics {
    private String serviceName;
    private double cpuUsage;
    private int replicas;
    private ServicePriority priority;

    public ServiceMetrics(String serviceName, double cpuUsage, int replicas, ServicePriority priority) {
        this.serviceName = serviceName;
        this.cpuUsage = cpuUsage;
        this.replicas = replicas;
        this.priority = priority;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public int getReplicas() {
        return replicas;
    }

    public ServicePriority getPriority() {
        return priority;
    }

    public void setReplicas(int replicas) {
        this.replicas = replicas;
    }
}
