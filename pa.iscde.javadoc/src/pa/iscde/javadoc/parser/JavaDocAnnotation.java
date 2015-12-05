package pa.iscde.javadoc.parser;

public class JavaDocAnnotation {

	private String tag;
	private String name;
	private String description;

	public JavaDocAnnotation(String tag, String description) {
		this.tag = tag;
		this.description = description;
	}

	public JavaDocAnnotation(String tag, String name, String description) {
		this.tag = tag;
		if (name != null && !name.equals("")) {
			this.name = name;
		}
		this.description = description;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}