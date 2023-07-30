package phong.example.discovery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication(exclude = { LiquibaseAutoConfiguration.class, RedisAutoConfiguration.class })
@Slf4j
public class DiscoveryApplication implements CommandLineRunner {

	@Value("${spring.cloud.config.uri}")
	private String CONFIG_SERVER_URI;

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Started eureka service with spring.profiles.cloud.config.uri = {}.", CONFIG_SERVER_URI);
	}
}
