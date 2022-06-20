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
        var message = new DeviceMessage(request.getDeviceId(), realEstateId, request.getContent(), request.getMessageType());
        // TODO: Fetch device configuration
        // If the device id does not belong to this house raise an error event and send it to admin application
        return deviceMessageRepository.save(message);
    }
}
