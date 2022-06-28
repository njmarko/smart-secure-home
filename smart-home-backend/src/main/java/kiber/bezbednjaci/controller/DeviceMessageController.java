package kiber.bezbednjaci.controller;

import kiber.bezbednjaci.dto.message.DeviceMessageRequest;
import kiber.bezbednjaci.dto.message.SearchMessagesRequest;
import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.service.DeviceConfigurationService;
import kiber.bezbednjaci.service.DeviceMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/device-messages")
public class DeviceMessageController {
    private final DeviceMessageService deviceMessageService;

    @Autowired
    public DeviceMessageController(DeviceMessageService deviceMessageService) {
        this.deviceMessageService = deviceMessageService;
    }

//    // TODO: Later, change this to RabbitListener once we switch to RabbitMQ
//    @PostMapping("{id}")
//    public void onMessageReceived(@PathVariable Integer id, @Valid @RequestBody DeviceMessageRequest request) {
//        deviceMessageService.save(id, request);
//    }
//
    @GetMapping
    public Page<DeviceMessage> read(SearchMessagesRequest request, Pageable pageable) {
        return deviceMessageService.read(request, pageable);
    }

    @RabbitListener(queues = "DEVICE_MESSAGES")
    public void publishPaperListener(DeviceMessageRequest msg){
        System.out.println(msg);
        try {
            deviceMessageService.save(msg.getObjectId(), msg);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}
