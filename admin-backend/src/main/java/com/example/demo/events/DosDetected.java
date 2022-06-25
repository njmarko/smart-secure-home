package com.example.demo.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

import lombok.Getter;
import lombok.Setter;

@Role(Type.EVENT)
@Expires("30m")
@Getter @Setter
public class DosDetected implements BaseAlarm {
	@Override
	public AlarmOccurred alarm() {
		return new AlarmOccurred("DOS detected.");
	}

}
