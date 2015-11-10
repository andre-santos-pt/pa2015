package pa.iscde.checkstyle.model;

import java.util.List;

public class Violation {
	
	private SeverityType severity;
	
	private String type;
	
	private int count;
	
	private String description;

	private List<ViolationDetail> details;
	
	public Violation(SeverityType severity, String type, int count, String description, List<ViolationDetail> details){
		this.severity = severity;
		this.type = type;
		this.count = count;
		this.description = description;
		this.setDetails(details);
	}
	
	public String getType() {
		return type; 
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ViolationDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ViolationDetail> details) {
		this.details = details;
	}

	public SeverityType getSeverity() {
		return severity;
	}

	public void setSeverity(SeverityType severity) {
		this.severity = severity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
