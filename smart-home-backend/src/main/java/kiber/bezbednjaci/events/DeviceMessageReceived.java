package kiber.bezbednjaci.events;

import kiber.bezbednjaci.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@Role(Role.Type.EVENT)
@Expires("1000m")
public class DeviceMessageReceived {


    private String id;
    private LocalDateTime timestamp;
    private Integer deviceId;
    private Integer realEstateId;
    private String content;
    private MessageType messageType;
    private Double value;
    private Boolean processed;

    public DeviceMessageReceived() {
        this.processed = false;
    }

    public DeviceMessageReceived(String id, LocalDateTime timestamp, Integer deviceId, Integer realEstateId, String content, MessageType messageType, Double value, Boolean processed) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.realEstateId = realEstateId;
        this.content = content;
        this.messageType = messageType;
        this.value = value;
        this.processed = processed;
    }
}
