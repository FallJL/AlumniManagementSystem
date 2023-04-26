package com.scu.ams.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.sql.DataSource;

@EnableDiscoveryClient
@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
public class AmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmsGatewayApplication.class, args);
    }

}
