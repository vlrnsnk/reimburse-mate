package com.vlrnsnk.reimbursemate;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReimburseMateApplication {

	public static void main(String[] args) {
		/*
		 * Load the environment variables from the .env file
		 */
		Dotenv dotenv = Dotenv.load();

		/*
		 * Set the environment variables to the system properties
		 */
		System.setProperty("DB_URL", dotenv.get("DATABASE_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DATABASE_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DATABASE_PASSWORD"));

		/*
		 * Start the Spring Boot Application
		 */
		SpringApplication.run(ReimburseMateApplication.class, args);

		System.out.println("ReimburseMate Application Started");
	}

}
