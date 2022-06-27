package kiber.bezbednjaci.service;

import kiber.bezbednjaci.model.DeviceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequiredArgsConstructor
@RequestScope
public class DeviceConfigurationService {
    private final RestTemplate restTemplate;

    public DeviceConfiguration read(Integer id) {
        return restTemplate.getForObject("https://localhost:8082/api/devices/" + id, DeviceConfiguration.class);
    }
}
