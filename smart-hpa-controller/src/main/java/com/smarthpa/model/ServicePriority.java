package com.smarthpa.model;

public enum ServicePriority {
    HIGH,
    MEDIUM,
    LOW;

    public static ServicePriority fromServiceName(String name) {
        switch (name) {
            case "order-service":
                return HIGH;
            case "payment-service":
                return MEDIUM;
            case "notification-service":
                return LOW;
            default:
                return LOW;
        }
    }
}
