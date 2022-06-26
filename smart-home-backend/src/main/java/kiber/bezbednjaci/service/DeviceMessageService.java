package kiber.bezbednjaci.service;

import kiber.bezbednjaci.dto.message.DeviceMessageRequest;
import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.repository.DeviceMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
@RequiredArgsConstructor
public class DeviceMessageService {
    private final DeviceMessageRepository deviceMessageRepository;
    private final DeviceConfigurationService deviceConfigurationService;

    public DeviceMessage save(Integer realEstateId, DeviceMessageRequest request) {
        var message = new DeviceMessage(request.getDeviceId(), realEstateId, request.getContent(), request.getMessageType(), request.getValue());
        var deviceConfiguration = deviceConfigurationService.read(request.getDeviceId());
        if (!deviceConfiguration.getRealEstateId().equals(realEstateId)) {
            throw new RuntimeException("Device id does not belong to this real estate.");
        }
        if (!request.getContent().matches(deviceConfiguration.getRegexFilter())) {
            throw new RuntimeException("Message received does not pass device's regex filter.");
        }
        // TODO: Insert the message in KieSession here...
        return deviceMessageRepository.save(message);
    }
}
