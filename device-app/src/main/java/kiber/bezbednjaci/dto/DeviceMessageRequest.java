package kiber.bezbednjaci.dto;

import kiber.bezbednjaci.model.MessageType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeviceMessageRequest {
    @NotNull(message = "Device ID is required.")
    private Integer deviceId;

    @NotBlank(message = "Content is required.")
    private String content;

    @NotNull(message = "Message type is required.")
    private MessageType messageType;

    @NotNull(message = "Value is required.")
    private Double value;
}
