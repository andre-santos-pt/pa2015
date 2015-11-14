package pa.iscde.checkstyle.internal.check;

import java.util.Map;

import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;

/**
 * 
 *
 */
public abstract class Check {
	protected String checkId;

	protected String message;

	protected String resource;

	protected SeverityType severity;

	protected Check(String checkId, String message, String resource, SeverityType severity) {
		this.checkId = checkId;
		this.setMessage(message);
		this.resource = resource;
		this.severity = severity;
	}

	public String getCheckId() {
		return checkId;
	}

	public SeverityType getSeverity() {
		return severity;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public abstract void process(Map<String, Violation> violations);
}
