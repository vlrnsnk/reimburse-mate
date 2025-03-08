package com.vlrnsnk.reimbursemate;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class ReimburseMateApplication {

	public static void main(String[] args) {
		/*
		 * Check if .env file exists in the current directory
		 * and load environment variables from it if present.
		 */
		Dotenv dotenv = null;
		if (Files.exists(Paths.get(".env"))) {
			dotenv = Dotenv.load(); // Load .env file locally
		}

		/*
		 * If dotenv is not null (i.e., file was found), use .env values
		 * Otherwise, fall back to system environment variables (for production)
		 */
		String dbUrl = dotenv != null ? dotenv.get("DATABASE_URL") : System.getenv("DATABASE_URL");
		String dbUsername = dotenv != null ? dotenv.get("DATABASE_USER") : System.getenv("DATABASE_USER");
		String dbPassword = dotenv != null ? dotenv.get("DATABASE_PASSWORD") : System.getenv("DATABASE_PASSWORD");

		/*
		 * Start the Spring Boot Application
		 */
		SpringApplication.run(ReimburseMateApplication.class, args);

		System.out.println("ReimburseMate Application Started");
	}

}
