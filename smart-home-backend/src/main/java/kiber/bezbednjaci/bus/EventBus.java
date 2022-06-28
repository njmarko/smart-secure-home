package kiber.bezbednjaci.bus;


import kiber.bezbednjaci.events.BaseAlarm;
import kiber.bezbednjaci.model.DeviceAlarmModel;
import kiber.bezbednjaci.repository.DeviceAlarmModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventBus {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DeviceAlarmModelRepository deviceAlarmModelRepository;

    public void onAlarm(BaseAlarm alarm) {
        DeviceAlarmModel alarmModel = new DeviceAlarmModel(alarm.getRealEstateId(), alarm.getDeviceId(), alarm.getMessage());
        System.out.println(alarm.getRealEstateId());
        this.simpMessagingTemplate.convertAndSend("/live-alarms/" + alarm.getRealEstateId(), alarm);
        this.deviceAlarmModelRepository.save(alarmModel);
    }

}
