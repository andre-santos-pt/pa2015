package pa.iscde.checkstyle.model;

import java.util.List;

/**
 * This class represents the concept of code violation.
 */
public class Violation {

	/**
	 * The violation severity.
	 */
	private SeverityType severity;

	/**
	 * The violation type.
	 */
	private String type;

	/**
	 * Number of occurrences of this violation type. 
	 */
	private int count;

	/**
	 * The violation main description.
	 */
	private String description;

	/**
	 * The list of violation details associated to this violation type.
	 */
	private List<ViolationDetail> details;

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
