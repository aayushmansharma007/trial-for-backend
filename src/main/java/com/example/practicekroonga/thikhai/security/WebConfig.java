// src/main/java/com/example/practicekroonga/thikhai/security/WebConfig.java
package com.example.practicekroonga.thikhai.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(
					"http://localhost:3000",
					"http://localhost:8080",
					"https://achar-website.vercel.app"
				)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
				.allowedHeaders("*")
				.exposedHeaders("Authorization")
				.allowCredentials(true)
				.maxAge(3600);
	}
}
