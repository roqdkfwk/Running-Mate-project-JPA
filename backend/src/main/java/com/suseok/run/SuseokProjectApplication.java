package com.suseok.run;

import com.suseok.run.config.EnvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuseokProjectApplication {

	public static void main(String[] args) {
		System.setProperty("MAIL_PASSWORD", EnvConfig.get("MAIL_PASSWORD"));

		SpringApplication.run(SuseokProjectApplication.class, args);
	}
}
