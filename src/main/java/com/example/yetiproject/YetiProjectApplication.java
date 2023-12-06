package com.example.yetiproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YetiProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(YetiProjectApplication.class, args);
	}

}
