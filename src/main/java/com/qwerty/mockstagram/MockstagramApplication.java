package com.qwerty.mockstagram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MockstagramApplication {

	public static void main(String[] args) {

		SpringApplication.run(MockstagramApplication.class, args);
	}
}
