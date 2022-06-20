package kiber.bezbednjaci.controller;

import kiber.bezbednjaci.dto.message.DeviceMessageRequest;
import kiber.bezbednjaci.service.DeviceMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/{id}/device-messages")
@RequiredArgsConstructor
public class DeviceMessageController {
    private final DeviceMessageService deviceMessageService;

    @PostMapping
    public void onMessageReceived(@PathVariable Integer id, @Valid @RequestBody DeviceMessageRequest request) {
        deviceMessageService.save(id, request);
    }
}
