package com.example.demo;

import com.example.demo.model.AlarmTemplate;
import com.example.demo.model.CertificateData;
import com.example.demo.repository.CertificateDataRepository;
import com.example.demo.service.AlarmTemplateService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class AdminApplication {
	private final CertificateDataRepository certificateDataRepository;
	private final KieSession kieSession;
	private final AlarmTemplateService alarmTemplateService;

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean
	public ApplicationRunner test() {
		return args -> {
			prepareTestMapping();
			prepareTestAlarmRules();
		};
	}

	private void prepareTestAlarmRules() {
		AlarmTemplate a1 = new AlarmTemplate("Stupid alarm rule 1", "String(this == \"This should never happen.\")", "", "I told you this should never happen.");
		AlarmTemplate a2 = new AlarmTemplate("Stupid alarm rule 2", "$s: String(this == \"This should never happen.\")", "System.out.println($s);", "Inaccessible alarm.");
		alarmTemplateService.createAlarmRule(a1);
		alarmTemplateService.createAlarmRule(a2);
	}

	private void prepareTestMapping() {
		CertificateData e1 = new CertificateData("root");
		CertificateData e2 = new CertificateData("superAdmin");
		certificateDataRepository.saveAllAndFlush(List.of(e1, e2));
	}
}
