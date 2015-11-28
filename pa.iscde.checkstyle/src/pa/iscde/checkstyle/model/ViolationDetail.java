package pa.iscde.checkstyle.model;

/**
 * This class represents the detail associated to a violation type.
 */
public class ViolationDetail {

	/**
	 * The violation severity.
	 */
	private SeverityType severity;

	/**
	 * The class element name on which the violation was detected.
	 */
	private String resource;

	/**
	 * The file system location of the class element which the violation was
	 * detected.
	 */
	private String location;

	/**
	 * Indicates the location in the class element where the violation was
	 * detected.
	 */
	private int offset;

	/**
	 * The line in the class element where the violation was detected.
	 */
	private int line;

	/**
	 * The length associated to check size violations.
	 */
	private int length;

	/**
	 * The detailed message associated to this violation detail.
	 */
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
