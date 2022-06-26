package kiber.bezbednjaci.service;

import kiber.bezbednjaci.dto.message.DeviceMessageRequest;
import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.repository.DeviceMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceMessageService {
    private final DeviceMessageRepository deviceMessageRepository;

    public DeviceMessage save(Integer realEstateId, DeviceMessageRequest request) {
        var message = new DeviceMessage(request.getDeviceId(), realEstateId, request.getContent(), request.getMessageType(), request.getValue());

        return deviceMessageRepository.save(message);
    }
}
