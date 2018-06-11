package co.com.grupoasd.services.fixedassets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FixedAssetsApplication {
	public static void main(String[] args) {
		SpringApplication.run(FixedAssetsApplication.class, args);
	}

}
