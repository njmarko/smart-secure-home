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
@Expires("20m")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidLoginAttempt implements BaseEvent {
	private String username;
	
	@Override
	public LogModel log() {
		return LogModel.warning(
				String.format("Invalid login attempt by '%s'.", username)
			);			
	}

}
