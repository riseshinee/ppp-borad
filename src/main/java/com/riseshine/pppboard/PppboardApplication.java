package com.riseshine.pppboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PppboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(PppboardApplication.class, args);
	}

}
