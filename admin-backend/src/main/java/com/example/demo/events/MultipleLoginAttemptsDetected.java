package com.example.demo.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Role(Type.EVENT)
@Expires("30m")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MultipleLoginAttemptsDetected implements BaseAlarm {
	private String username;

	@Override
	public AlarmOccurred alarm() {
		return new AlarmOccurred(
				String.format("%s has tried to login multiple times and failed.", this.username)
			);
	}
}
