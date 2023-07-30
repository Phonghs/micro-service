package phong.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@Slf4j
@EnableConfigServer
public class ConfigApplication implements CommandLineRunner {

	@Value("${spring.cloud.config.server.native.search-locations}")
	private String SEARCH_LOCATIONS;

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Config server starting with spring.cloud.config.server.native.searchLocations = {}.", SEARCH_LOCATIONS);
	}
}
