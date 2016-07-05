package org.component.criticality.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MetricsApp {
    public static void main(String[] args) {
        SpringApplication.run(MetricsApp.class, args);
    }
}
