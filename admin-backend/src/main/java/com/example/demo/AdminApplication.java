package com.example.demo;

import com.example.demo.model.AlarmTemplate;
import com.example.demo.model.CertificateData;
import com.example.demo.model.DeviceAlarmTemplate;
import com.example.demo.repository.CertificateDataRepository;
import com.example.demo.service.AlarmTemplateService;
import com.example.demo.service.DeviceAlarmTemplateService;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
public class AdminApplication {
    private final CertificateDataRepository certificateDataRepository;
    private final KieSession kieSession;
    private final AlarmTemplateService alarmTemplateService;

    private final DeviceAlarmTemplateService deviceAlarmTemplateService;

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

    @Bean
    public ApplicationRunner test() {
        return args -> {
            prepareTestMapping();
            prepareTestAlarmRules();
            prepareTestDeviceAlarmRules();
        };
    }

    private void prepareTestAlarmRules() {
        alarmTemplateService.deleteAll();
        AlarmTemplate a1 = new AlarmTemplate("Stupid alarm rule 1", "String(this == \"This should never happen.\")", "", "I told you this should never happen.");
        AlarmTemplate a2 = new AlarmTemplate("Stupid alarm rule 2", "$s: String(this == \"This should never happen.\")", "System.out.println($s);", "Inaccessible alarm.");
        alarmTemplateService.createAlarmRule(a1);
        alarmTemplateService.createAlarmRule(a2);
    }

    private void prepareTestDeviceAlarmRules() {
        deviceAlarmTemplateService.deleteAll();
        DeviceAlarmTemplate a1 = new DeviceAlarmTemplate(
                "Camera",
                "    e1: DeviceMessageReceived(deviceId == 1, realEstateId == 1, value >= 1)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 1, realEstateId == 1,value >= 1,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )" +
                        "", "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Camera detection happened\"));", "Camera detection happened");
        DeviceAlarmTemplate a2 = new DeviceAlarmTemplate(
                "Light switch turned on",
                "    e1: DeviceMessageReceived(deviceId == 2, realEstateId == 1, value >= 1)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 2, realEstateId == 1,value >= 1,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )",
                "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Detected: Light switch turned on\"));",
                "Light switch turned on");

        DeviceAlarmTemplate a3 = new DeviceAlarmTemplate(
                "Unsuccessful unlock attempt",
                "    e1: DeviceMessageReceived(deviceId == 3, realEstateId == 2, value >= 3, value < 4)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 3, realEstateId == 2,value >= 3, value < 4,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )",
                "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Detected: Unsuccessfull unlock attempt\"));",
                "Unsuccessful unlock attempt");

        DeviceAlarmTemplate a4 = new DeviceAlarmTemplate(
                "Door handle pulled while doors were locked",
                "    e1: DeviceMessageReceived(deviceId == 3, realEstateId == 2, value >= 2, value < 3)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 3, realEstateId == 2,value >= 2, value < 3,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )",
                "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Detected: Door handle pulled while doors were locked\"));",
                "Door handle pulled while doors were locked");

        DeviceAlarmTemplate a5 = new DeviceAlarmTemplate(
                "Air Humidity Meter Measured Humidity that is too high",
                "    e1: DeviceMessageReceived(deviceId == 4, realEstateId == 3, value >= 55, value < 200)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 4, realEstateId == 3,value >= 55, value < 200,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )",
                "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Detected: Air Humidity Meter Measured Humidity that is too high\"));",
                "Air Humidity Meter Measured Humidity that is too high");

        DeviceAlarmTemplate a6 = new DeviceAlarmTemplate(
                "Thermometer measured Temperature that is too high",
                "    e1: DeviceMessageReceived(deviceId == 5, realEstateId == 3, value >= 22, value < 200)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 5, realEstateId == 3,value >= 22, value < 200,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )",
                "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Detected: Thermometer measured Temperature that is too high\"));",
                "Thermometer measured Temperature that is too high");

        DeviceAlarmTemplate a7 = new DeviceAlarmTemplate(
                "Barometer measured Air pressure that is too high",
                "    e1: DeviceMessageReceived(deviceId == 6, realEstateId == 1, value >= 1030, value < 10000)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 6, realEstateId == 1,value >= 1030, value < 10000,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )",
                "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Detected: Barometer measured Air pressure that is too high\"));",
                "Barometer measured Air pressure that is too high");

        DeviceAlarmTemplate a8 = new DeviceAlarmTemplate(
                "Heart rate monitor measured Heart rate that is too high",
                "    e1: DeviceMessageReceived(deviceId == 7, realEstateId == 1, value >= 170, value < 300)\n" +
                        "    Number(intValue >= 3) from accumulate(\n" +
                        "        e2: DeviceMessageReceived(\n" +
                        "        deviceId == 7, realEstateId == 1,value >= 170, value < 300,\n" +
                        "            this != e1,\n" +
                        "            this meets[5m] e1\n" +
                        "        ),\n" +
                        "        count(e2)\n" +
                        "    )\n" +
                        "    not(\n" +
                        "        DeviceAlarmTriggered(realEstateId == e1.getRealEstateId(), deviceId == e1.getDeviceId())\n" +
                        "    )",
                "insert(new DeviceAlarmTriggered(e1.getRealEstateId(), e1.getDeviceId(), \"Detected: Heart rate monitor measured Heart rate that is too high\"));",
                "Heart rate monitor measured Heart rate that is too high");

        deviceAlarmTemplateService.createAlarmRule(a1);
        deviceAlarmTemplateService.createAlarmRule(a2);
        deviceAlarmTemplateService.createAlarmRule(a3);
        deviceAlarmTemplateService.createAlarmRule(a4);
        deviceAlarmTemplateService.createAlarmRule(a5);
        deviceAlarmTemplateService.createAlarmRule(a6);
        deviceAlarmTemplateService.createAlarmRule(a7);
        deviceAlarmTemplateService.createAlarmRule(a8);
    }

    private void prepareTestMapping() {
        CertificateData e1 = new CertificateData("root");
        CertificateData e2 = new CertificateData("superAdmin");
        certificateDataRepository.saveAllAndFlush(List.of(e1, e2));
    }
}
