package com.roms.api;

import com.roms.api.controller.LeaveRequestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Random;
import java.util.random.RandomGenerator;

@SpringBootApplication
public class RomsApplication {
	public static final Logger logger = LoggerFactory.getLogger(RomsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RomsApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/v1/**").allowedOrigins("*").allowedHeaders("*").allowCredentials(false).allowedMethods("*");
			}
		};
	}

}
