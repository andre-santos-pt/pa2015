package pa.iscde.checkstyle.internal.config;

public class ConfigProperty {
	private String name;

	private String value;

	private boolean isNumeric;

	public ConfigProperty(String name, String value, boolean isNumeric) {
		this.name = name;
		this.value = value;
		this.isNumeric = isNumeric;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public boolean isNumeric() {
		return isNumeric;
	}
}
