package kiber.bezbednjaci.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepository;
	
	public AlarmModel save(AlarmModel alarm) {
		return alarmRepository.save(alarm);
	}

	public Page<AlarmModel> read(Pageable pageable) {
		return alarmRepository.readNonAcknowledged(pageable);
	}

	public void acknowledge(String id) {
		AlarmModel alarm = alarmRepository.findById(id).orElse(null);
		if (alarm == null) {
			return;
		}
		alarm.setAcknowledged(true);
		alarmRepository.save(alarm);
	}
}
