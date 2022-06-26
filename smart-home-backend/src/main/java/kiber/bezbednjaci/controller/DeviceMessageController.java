package kiber.bezbednjaci.controller;

import kiber.bezbednjaci.dto.message.DeviceMessageRequest;
import kiber.bezbednjaci.dto.message.SearchMessagesRequest;
import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.service.DeviceConfigurationService;
import kiber.bezbednjaci.service.DeviceMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/device-messages")
@RequiredArgsConstructor
public class DeviceMessageController {
    private final DeviceMessageService deviceMessageService;
    private final DeviceConfigurationService deviceConfigurationService;

    // TODO: Later, change this to RabbitListener once we switch to RabbitMQ
    @PostMapping
    public void onMessageReceived(@PathVariable Integer id, @Valid @RequestBody DeviceMessageRequest request) {
        deviceMessageService.save(id, request);
    }

    @GetMapping
    public Page<DeviceMessage> read(SearchMessagesRequest request, Pageable pageable) {
        return deviceMessageService.read(request, pageable);
    }
}
