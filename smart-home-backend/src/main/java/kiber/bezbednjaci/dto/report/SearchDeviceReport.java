package kiber.bezbednjaci.dto.report;

import kiber.bezbednjaci.model.MessageType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class SearchDeviceReport {
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @NotNull(message = "regex is required.")
    @Length(max = 2000)
    private String regex = "";
    @NotNull(message = "Date from is required.")
    @Length(max = 2000)
    private String from = null;
    @NotNull(message = "Date to is required.")
    @Length(max = 2000)
    private String to = null;

    public LocalDateTime fromTimestamp() {
        LocalDateTime time = LocalDateTime.now().minusDays(7);
        if (this.from != null) {
            time = LocalDate.parse(this.from, TIMESTAMP_FORMAT).atStartOfDay();
        }
        return time;
    }

    public LocalDateTime toTimestamp() {
        LocalDateTime time = LocalDateTime.now().plusDays(7);
        if (this.to != null) {
            time = LocalDate.parse(this.to, TIMESTAMP_FORMAT).plusDays(1).atStartOfDay();
        }
        return time;
    }
}
