package pa.iscde.checkstyle.model;

public enum SeverityType {
	BLOCKED("Blocked", "blocked.png"), CRITICAL("Critical", "critical.png"), WARNING("Warning", "warning.png");

	private final String name;

	private final String image;

	SeverityType(String name, String image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}
}
