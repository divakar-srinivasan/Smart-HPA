package com.smarthpa.controller;

import com.smarthpa.model.ServiceMetrics;
import com.smarthpa.service.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * DashboardController exposes a web dashboard at /dashboard
 * to visualize Kubernetes cluster status and scaling decisions.
 * It retrieves real-time metrics from MetricsService and passes them to the
 * Thymeleaf view.
 */
@Controller
public class DashboardController {
    @Autowired
    private MetricsService metricsService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Collect cluster metrics and pass to Thymeleaf view
        List<ServiceMetrics> services = metricsService.getServiceMetrics("default");
        boolean resourceExhausted = services.stream().anyMatch(s -> s.getCpuUsage() > 90);
        model.addAttribute("services", services);
        model.addAttribute("resourceExhausted", resourceExhausted);
        return "dashboard";
    }
}
