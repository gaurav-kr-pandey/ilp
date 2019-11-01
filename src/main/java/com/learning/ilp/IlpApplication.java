package com.learning.ilp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.learning.ilp.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class IlpApplication {

	public static void main(String[] args) {
		SpringApplication.run(IlpApplication.class, args);
	}

}
