package kiber.bezbednjaci.model;

import lombok.Data;

@Data
public class DeviceConfiguration {
    private Integer id;
    private Integer realEstateId;
    private String name;
    private String regexFilter;
    private Integer sendRate;
}
