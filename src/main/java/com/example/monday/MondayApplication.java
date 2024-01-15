package com.example.monday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MondayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MondayApplication.class, args);
	}

}
