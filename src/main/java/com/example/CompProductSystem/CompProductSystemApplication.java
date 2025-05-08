package com.example.CompProductSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CompProductSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompProductSystemApplication.class, args);
	}


}
