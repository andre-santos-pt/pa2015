package pa.iscde.javadoc.parser.structure;

import pa.iscde.javadoc.internal.JavaDocTagI;

public class JavaDocAnnotation {

	private JavaDocTagI tag;
	private String name;
	private String description;

	public JavaDocAnnotation(JavaDocTagI tag, String description) {
		this.tag = tag;
		this.description = description;
	}

	public JavaDocAnnotation(JavaDocTagI tag, String name, String description) {
		this.tag = tag;
		if (name != null && !name.equals("")) {
			this.name = name;
		}
		if (description != null && !description.equals("")) {
			this.description = description;
		}
	}

	public JavaDocTagI getTag() {
		return tag;
	}

	public void setTag(JavaDocTagI tag) {
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

	public String getTagName() {
		return this.tag.getTagName();
	}
}