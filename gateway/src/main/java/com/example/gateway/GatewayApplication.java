package com.example.gateway;

import com.example.common.proxy.config.EnableAllProxyService;
import com.example.common.proxy.config.EnableBaseProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@EnableAllProxyService
@EnableBaseProxyService
@EnableEurekaClient
//@EnableRestCommonApi
public class GatewayApplication implements CommandLineRunner {
	@Value("${spring.cloud.config.uri}")
	private String CONFIG_SERVER_URI;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Started cloud-gateway with spring.profiles.cloud.config.uri = {}.", CONFIG_SERVER_URI);
	}
}
