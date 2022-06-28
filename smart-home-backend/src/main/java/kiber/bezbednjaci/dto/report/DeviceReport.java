package kiber.bezbednjaci.dto.report;

import lombok.Data;

@Data
public class DeviceReport {
    private String deviceId;
    private String estateId;
    private Integer alarmNumber;
}
