package kiber.bezbednjaci.events;

import kiber.bezbednjaci.model.MessageType;
import lombok.Getter;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@Role(Role.Type.EVENT)
@Expires("3m")
public class DeviceAlarmTriggered implements BaseAlarm{

    private String id;
    private LocalDateTime timestamp;
    private Integer deviceId;
    private Integer realEstateId;
    private String content;
    private MessageType messageType;
    private Double value;

    @Override
    public AlarmOccurred alarm() {
        return new AlarmOccurred("Device alarm triggered.");
    }

    public DeviceAlarmTriggered() {
    }

    public DeviceAlarmTriggered(String id, LocalDateTime timestamp, Integer deviceId, Integer realEstateId, String content, MessageType messageType, Double value) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.realEstateId = realEstateId;
        this.content = content;
        this.messageType = messageType;
        this.value = value;
    }
}
