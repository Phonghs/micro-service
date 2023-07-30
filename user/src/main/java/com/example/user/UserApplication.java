package com.example.user;

import com.example.common.proxy.config.EnableAllProxyService;
import com.example.common.proxy.config.error.EnableRestCommonApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@EnableRestCommonApi
@EnableAllProxyService
public class UserApplication implements CommandLineRunner {
	@Value("${spring.cloud.config.uri}")
	private String CONFIG_SERVER_URI;

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Started user-service with spring.profiles.cloud.config.uri = {}.", CONFIG_SERVER_URI);
	}
}
