package com.ssafy.living_spot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class LivingSpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivingSpotApplication.class, args);
	}

}
