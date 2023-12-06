package com.example.yetiproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.yetiproject.auth.AuthenticationSuccessHandlerImpl;

@Configuration
public class SecurityBeansConfig {
	@Bean
	public AuthenticationSuccessHandlerImpl authenticationSuccessHandler() {
		return new AuthenticationSuccessHandlerImpl();
	}
}
