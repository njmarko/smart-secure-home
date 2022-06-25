package com.example.demo.events;

import com.example.demo.logging.LogModel;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Role(Type.EVENT)
@Expires("10m")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RequestReceived implements BaseEvent {
	private String method;

	@Override
	public LogModel log() {
		return LogModel.info("Request received to method: " + method);
	}

}
