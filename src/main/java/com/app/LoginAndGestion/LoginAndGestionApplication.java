package com.app.LoginAndGestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoginAndGestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAndGestionApplication.class, args);
	}

}
