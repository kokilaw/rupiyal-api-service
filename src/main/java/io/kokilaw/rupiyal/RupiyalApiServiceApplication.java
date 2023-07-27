package io.kokilaw.rupiyal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RupiyalApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RupiyalApiServiceApplication.class, args);
	}

}
