package kiber.bezbednjaci.service;

import kiber.bezbednjaci.dto.message.DeviceMessageRequest;
import kiber.bezbednjaci.dto.message.SearchMessagesRequest;
import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.repository.DeviceMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
@RequiredArgsConstructor
public class DeviceMessageService {
    private final DeviceMessageRepository deviceMessageRepository;
    private final DeviceConfigurationService deviceConfigurationService;

    public DeviceMessage save(Integer realEstateId, DeviceMessageRequest request) {
        DeviceMessage message = new DeviceMessage(request.getDeviceId(), realEstateId, request.getContent(), request.getMessageType(), request.getValue());
        kiber.bezbednjaci.model.DeviceConfiguration deviceConfiguration = deviceConfigurationService.read(request.getDeviceId());
        if (!deviceConfiguration.getRealEstateId().equals(realEstateId)) {
            throw new RuntimeException("Device id does not belong to this real estate.");
        }
        if (!request.getContent().matches(deviceConfiguration.getRegexFilter())) {
            throw new RuntimeException("Message received does not pass device's regex filter.");
        }
        // TODO: Insert the message in KieSession here...
        return deviceMessageRepository.save(message);
    }

    public Page<DeviceMessage> read(SearchMessagesRequest request, Pageable pageable) {
        return deviceMessageRepository.search(request.getRealEstateId(), request.getMessageType(), request.getRegex(), request.fromTimestamp(), request.toTimestamp(), pageable);
    }
}
