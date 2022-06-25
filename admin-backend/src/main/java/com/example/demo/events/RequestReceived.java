package com.example.demo.events;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.logging.LogModel;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

import lombok.Getter;
import lombok.Setter;

@Role(Type.EVENT)
@Expires("10m")
@Getter @Setter
public class RequestReceived implements BaseEvent {
	private String method;
	private HttpServletRequest request;
	private Boolean processed;
	private String ip;
	
	public RequestReceived() {
		this.processed = false;
	}
	
	public RequestReceived(String method, HttpServletRequest request) {
		this();
		this.method = method;
		this.request = request;
		this.ip = request.getRemoteAddr();
	}

	@Override
	public LogModel log() {
		return LogModel.info("Request received to method: " + method + ", to path: " + request.getRequestURI());
	}

}
