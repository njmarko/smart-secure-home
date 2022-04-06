package com.example.demo;

import com.example.demo.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminApplication {

	private final CertificatesService certificatesService;

	@Autowired
	public AdminApplication(CertificatesService certificatesService) {
		this.certificatesService = certificatesService;
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean
	public ApplicationRunner test(){
		return args->certificatesService.showKeyStoreContent();
	}
}
