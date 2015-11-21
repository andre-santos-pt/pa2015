package pt.iscde.classdiagram.model.zest;

import java.util.ArrayList;
import java.util.List;

import pt.iscde.classdiagram.model.EClassType;

public class MyNode {
	private final String id;
	private final String name;
	private final EClassType classType;
	private List<MyNode> connections;

	public MyNode(String id, String name, EClassType classType) {
		this.id = id;
		this.name = name;
		this.classType = classType;
		this.connections = new ArrayList<MyNode>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public EClassType getClassType(){
		return classType;
	}

	public List<MyNode> getConnectedTo() {
		return connections;
	}

}
