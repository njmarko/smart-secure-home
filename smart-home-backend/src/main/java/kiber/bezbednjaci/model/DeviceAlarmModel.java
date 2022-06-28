package kiber.bezbednjaci.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kiber.bezbednjaci.util.JsonDateSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
public class DeviceAlarmModel {
    @Id
    private String id;
    private String message;
    private Integer realEstateId;
    private Integer deviceId;
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime timestamp;
    private Boolean acknowledged;

    public DeviceAlarmModel() {
        this.timestamp = LocalDateTime.now();
        this.acknowledged = false;
    }

    public DeviceAlarmModel(Integer realEstateId, Integer deviceId, String message) {
        this();
        this.realEstateId = realEstateId;
        this.deviceId = deviceId;
        this.message = message;
    }
}
