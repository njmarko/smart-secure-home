package com.example.demo.events;

import java.io.Serializable;

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
public class MultipleLoginAttemptsDetected implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
}
