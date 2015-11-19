package pt.iscde.classdiagram.model;

public class ClassAttribute {
	private EVisibility visibility;
	private String name;
	private String type;

	public EVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(EVisibility visibility) {
		this.visibility = visibility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
