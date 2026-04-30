package com.psx.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  // This makes it a service registry
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
        System.out.println("""
        ╔═══════════════════════════════════════════╗
        ║   🧭 Eureka Service Discovery Running     ║
        ║   Dashboard: http://localhost:8761        ║
        ║   All services will register here         ║
        ╚═══════════════════════════════════════════╝
        """);
    }
}