package kiber.bezbednjaci.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmOccurred {
	private String message;
	private LocalDateTime timestamp;
	
	public AlarmOccurred(String message) {
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}
}
