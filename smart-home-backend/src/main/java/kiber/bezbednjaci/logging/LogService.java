package kiber.bezbednjaci.logging;

import kiber.bezbednjaci.events.BaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public void save(BaseEvent event) {
        this.save(event.log());
    }

    public void save(LogModel log) {
        logRepository.save(log);
    }

    public void info(String message) {
        saveLogType(message, LogType.INFO);
    }

    public void warning(String message) {
        saveLogType(message, LogType.WARNING);
    }

    public void error(String message) {
        saveLogType(message, LogType.ERROR);
    }

    private void saveLogType(String message, LogType type) {
        save(new LogModel(message, type));
    }
}
