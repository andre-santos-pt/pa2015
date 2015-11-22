package pt.iscde.classdiagram.model.zest;

import java.util.ArrayList;
import java.util.List;

import pt.iscde.classdiagram.model.MyConnection;
import pt.iscde.classdiagram.model.MyNode;

public class NodeModelContentProvider{
	private List<MyConnection> connections;
	private List<MyNode> nodes;

	public NodeModelContentProvider() {
		nodes = new ArrayList<MyNode>();
		connections = new ArrayList<MyConnection>();

		for (MyConnection connection : connections) {
			connection.getSource().getConnectedTo().add(connection.getDestination());
		}
	}

	public List<MyNode> getNodes() {
		return nodes;
	}

	public void addConnection(MyConnection connection) {
		this.connections.add(connection);
		for (MyConnection conn : connections) {
			conn.getSource().getConnectedTo().add(connection.getDestination());
		}
	}
}
