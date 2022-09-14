package com.surya.dansbetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaRepositories({ "com.surya.dansbetest.repository" })
public class DansbetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DansbetestApplication.class, args);
	}

}
