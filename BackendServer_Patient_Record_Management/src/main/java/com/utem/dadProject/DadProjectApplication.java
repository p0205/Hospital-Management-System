package com.utem.dadProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.utem.dadProject.repo"})
@EntityScan("com.utem.dadProject.model")
public class DadProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DadProjectApplication.class, args);
	}

}
