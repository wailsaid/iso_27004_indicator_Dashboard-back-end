package com.pfem2.iso27004;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class Iso27004Application {

	public static void main(String[] args) {
		SpringApplication.run(Iso27004Application.class, args);
	}

}
