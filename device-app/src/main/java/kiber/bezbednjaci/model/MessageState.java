package kiber.bezbednjaci.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageState {
    private double minVal;
    private double maxVal;
    private int numMessages;
    private String messageContent;
    private String measureUnit;
}
