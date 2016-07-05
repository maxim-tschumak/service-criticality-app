package org.component.criticality.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ServiceCriticalityWebApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCriticalityWebApp.class, args);
    }
}
