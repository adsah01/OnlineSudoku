package com.example.Controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SolverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolverApplication.class, args);
	}


}
