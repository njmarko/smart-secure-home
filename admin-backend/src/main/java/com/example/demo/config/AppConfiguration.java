package com.example.demo.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.example.demo.bus.EventBus;

import org.drools.core.ClockType;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@Slf4j
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
    
    @Bean
	public KieSession kieSession(EventBus eventBus) {
    	log.info("Starting template compilation...");
    	try {
			processMaliciousIpAddresses();
			log.info("Compiled templates...");
		} catch (Exception e) {
			log.error("Failed to generate rules for processing malicious IP addresses");
		}
    	
    	log.info("Starting kjar compilation...");
    	try {
			compileKrajProject();
			log.info("Compiled kjar project...");
		} catch (Exception e) {
			log.error("Failed to compile kjar project.");
		}
    	
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("rs.bsep", "rules-kjar", "0.0.1-SNAPSHOT"));
		KieBaseConfiguration kieBaseConfiguration = ks.newKieBaseConfiguration();
		kieBaseConfiguration.setOption(EventProcessingOption.STREAM);
		KieBase kieBase = kContainer.newKieBase(kieBaseConfiguration);

		KieSessionConfiguration kieSessionConfiguration = ks.newKieSessionConfiguration();
		kieSessionConfiguration.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
		
		kieSession.setGlobal("eventBus", eventBus);
		
		return kieSession;
	}

    // Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
    // BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    private void processMaliciousIpAddresses() throws Exception {
        InputStream template = new FileInputStream("../rules-kjar/src/main/resources/templates/malicious-ip.drt");
        // TODO: Load from some configuration file or something like that
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
            new String[]{"adresa 1"},
            new String[]{"adresa 2"},
            new String[]{"adresa 3"}
        });
        DataProviderCompiler converter = new DataProviderCompiler();
        String compiledTemplateInstance = converter.compile(dataProvider, template);
        try (FileWriter writter = new FileWriter("../rules-kjar/src/main/resources/rules/malicious-ip.drl")) {
        	writter.write(compiledTemplateInstance);
        }
    }
    
    private void compileKrajProject() throws Exception {
    	String workingDir = System.getProperty("user.dir");
    	String command = workingDir + "\\compileKjar.bat";
    	Runtime.getRuntime().exec(command).waitFor();
    }
}
