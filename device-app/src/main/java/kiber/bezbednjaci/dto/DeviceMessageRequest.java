package kiber.bezbednjaci.dto;

import kiber.bezbednjaci.model.MessageType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMessageRequest implements Serializable {

    @NotNull(message = "Object ID is required.")
    private Integer objectId;

    @NotNull(message = "Device ID is required.")
    private Integer deviceId;

    @NotBlank(message = "Content is required.")
    private String content;

    @NotNull(message = "Message type is required.")
    private MessageType messageType;

    @NotNull(message = "Value is required.")
    private Double value;
}
