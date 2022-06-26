package kiber.bezbednjaci.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
public class DeviceMessage {
    @Id
    private String id;
    private LocalDateTime timestamp;
    private Integer deviceId;
    private Integer realEstateId;
    private String content;
    private MessageType messageType;
    private Double value;

    public DeviceMessage() {
        super();
    }

    public DeviceMessage(Integer deviceId, Integer realEstateId, String content, MessageType messageType, Double value) {
        this();
        this.deviceId = deviceId;
        this.realEstateId = realEstateId;
        this.content = content;
        this.messageType = messageType;
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }
}
