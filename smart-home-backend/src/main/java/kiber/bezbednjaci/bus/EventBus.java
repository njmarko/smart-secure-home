package kiber.bezbednjaci.bus;


import kiber.bezbednjaci.events.AlarmOccurred;
import kiber.bezbednjaci.events.BaseAlarm;
import kiber.bezbednjaci.logging.AlarmModel;
import kiber.bezbednjaci.logging.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventBus {

    private final AlarmService alarmService;

    public void onAlarm(AlarmOccurred alarmOccured) {
        System.out.println(alarmOccured.getMessage());
    }

    public void onAlarm(BaseAlarm alarm) {
        AlarmOccurred alarmOccurred = alarm.alarm();
        AlarmModel alarmModel = new AlarmModel(alarmOccurred.getMessage());
        this.onAlarm(alarmOccurred);
        this.alarmService.save(alarmModel);
    }

}
