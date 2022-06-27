package kiber.bezbednjaci.logging;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kiber.bezbednjaci.util.JsonDateSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter @Setter
public class LogModel {
    @Id
    private String id;
    private String content;
    @JsonSerialize(using = JsonDateSerializer.class)
    private LocalDateTime timestamp;
    private LogType logType;

    public LogModel() {
        this.timestamp = LocalDateTime.now();
    }

    public LogModel(String content, LogType logType) {
        this();
        this.content = content;
        this.logType = logType;
    }
    
    public static LogModel info(String content) {
    	return new LogModel(content, LogType.INFO);
    }
    
    public static LogModel warning(String content) {
    	return new LogModel(content, LogType.WARNING);
    }
    
    public static LogModel error(String content) {
    	return new LogModel(content, LogType.ERROR);
    }
}
