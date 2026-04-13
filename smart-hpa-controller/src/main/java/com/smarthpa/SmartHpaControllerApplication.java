package com.smarthpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Enables Spring's scheduling capability
public class SmartHpaControllerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartHpaControllerApplication.class, args);
    }
}
