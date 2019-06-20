package com.webapplication.WebApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages={
		"com.webapplication.Controller",
		"com.webapplication.Repository", "com.webapplication.Service",
		"com.webapplication.JSONBeans"})
@EnableJpaRepositories("com.webapplication.Repository")
@EntityScan("com.webapplication.Model")
@EnableTransactionManagement
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
