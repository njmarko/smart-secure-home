package kiber.bezbednjaci.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "device_message")
public class DeviceMessage extends BaseEntity {
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "device_id", nullable = false)
    private Integer deviceId;

    @Column(name = "real_estate_id", nullable = false)
    private Integer realEstateId;

    @Column(name = "content", length = 2000, nullable = false)
    private String content;

    @Column(name = "message_type", nullable = false)
    private MessageType messageType;

    @Column(name = "value", nullable = false)
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
