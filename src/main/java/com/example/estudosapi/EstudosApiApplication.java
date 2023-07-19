package com.example.estudosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EstudosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstudosApiApplication.class, args);
	}

}
