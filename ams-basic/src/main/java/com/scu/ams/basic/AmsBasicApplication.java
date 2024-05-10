package com.scu.ams.basic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
@MapperScan("com.scu.ams.basic.dao")
@EnableAsync // 开启异步调用
public class AmsBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmsBasicApplication.class, args);
	}
}
