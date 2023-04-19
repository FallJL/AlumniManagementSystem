package com.scu.ams.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmsGatewayApplication.class, args);
    }

}
