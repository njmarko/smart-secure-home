package kiber.bezbednjaci.events;

import kiber.bezbednjaci.logging.LogModel;
import lombok.Getter;
import lombok.Setter;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import javax.servlet.http.HttpServletRequest;

@Getter @Setter
@Role(Role.Type.EVENT)
@Expires("100m")
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
		return LogModel.info("Request received to path: "+ request.getRequestURI() + ", from IP " + this.ip);
	}

}
