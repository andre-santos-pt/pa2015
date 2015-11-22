package pt.iscde.classdiagram.model;

public class MyConnection {
	final String id;
	final String label;
	final MyTopLevelElement source;
	final MyTopLevelElement destination;

	public MyConnection(String id, String label, MyTopLevelElement source, MyTopLevelElement destination) {
		this.id = id;
		this.label = label;
		this.source = source;
		this.destination = destination;
	}

	public String getLabel() {
		return label;
	}

	public MyTopLevelElement getSource() {
		return source;
	}

	public MyTopLevelElement getDestination() {
		return destination;
	}
}
