package com.example.demo;

import java.util.List;

import com.example.demo.bus.EventBus;
import com.example.demo.dto.AlarmOccured;
import com.example.demo.model.CertificateData;
import com.example.demo.repository.CertificateDataRepository;

import org.kie.api.runtime.KieSession;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class AdminApplication {
	private final CertificateDataRepository certificateDataRepository;
	private final KieSession kieSession;
	private final EventBus eventBus;

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean
	public ApplicationRunner test() {
		return args -> {
			prepareTestMapping();
			kieSession.insert("Go");
			kieSession.fireAllRules();
			while (true) {
				eventBus.onAlarm(new AlarmOccured("Okinuo se alarm..."));
				Thread.sleep(1000L);
			}
		};
	}

	public void prepareTestMapping() {
		CertificateData e1 = new CertificateData("root");
		CertificateData e2 = new CertificateData("superAdmin");
		certificateDataRepository.saveAllAndFlush(List.of(e1, e2));
	}
}
