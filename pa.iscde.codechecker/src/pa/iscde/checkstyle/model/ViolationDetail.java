package pa.iscde.checkstyle.model;

public class ViolationDetail {
	private String resource;
	
	private String location;
	
	private String line;
	
	private String message;

	public ViolationDetail(String resource, String location, String line, String message){
		this.resource = resource;
		this.location = location;
		this.line = line;
		this.message = message;
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

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
