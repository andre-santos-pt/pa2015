package pa.iscde.checkstyle.model;

public class ViolationDetail {
	private SeverityType severity;
	
	private String resource;

	private String location;

	private int offset;

	private int line;

	private int length;

	private String message;

	public SeverityType getSeverity() {
		return severity;
	}

	public void setSeverity(SeverityType severity) {
		this.severity = severity;
	}
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
