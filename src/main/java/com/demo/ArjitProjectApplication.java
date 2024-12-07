package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ArjitProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArjitProjectApplication.class, args);
	}

}
