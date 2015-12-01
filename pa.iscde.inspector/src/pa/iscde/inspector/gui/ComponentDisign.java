package pa.iscde.inspector.gui;

import java.awt.Color;
import java.awt.Event;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;
import org.eclipse.zest.core.widgets.ZestStyles;

import pa.iscde.inspector.component.ComponentData;
import pa.iscde.inspector.component.Extension;
import pa.iscde.inspector.component.ExtensionPoint;

public class ComponentDisign {
	private Color color;
	private boolean isAtive;
	private GraphNode node;
	private Graph graph;
	private List<GraphConnection> graphConnections;
	private ComponentData componentData;
	private List<Extension> extensions;

	public ComponentDisign(ComponentData component) {
		this.componentData = component;
		extensions = component.getExtensions();
		color = Color.BLUE;
		isAtive = true;
		graphConnections = new ArrayList<GraphConnection>();

	}

	public void setExtensionPointOwnerDesign() {
		for (ExtensionPoint extensionPoint : componentData.getExtensionPoints()) {
			if (extensionPoint != null) {
				extensionPoint.setOwnerDesign(this);
			}
		}
	}

	public ComponentData getComponentData() {
		return componentData;
	}

	public Graph getGraph() {
		return graph;
	}

	public List<GraphConnection> getGraphConnections() {
		return graphConnections;
	}

	public GraphNode getNode() {
		return node;
	}

	public void drawNode(IContainer graphModel) {
		graph = (Graph) graphModel;
		node = new GraphNode(graph, SWT.NONE, getName(), this);
	}

	public void drawConnections() {
		for (Extension extension : extensions) {
			if (extension.getExtensionPoint() != null) {
				GraphConnection graphConnection = new GraphConnection(graph, SWT.NONE, node,
						extension.getExtensionPoint().getOwnerDesign().node);
				graphConnection.setText(extension.getConnectionName());
				graphConnection.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
				graphConnections.add(graphConnection);
			}
		}
//		if (graphConnections.size() > 1) {
//			handleOverlappindConnection();
//		}
	}

	private void handleOverlappindConnection() {
		int i = 0;
		StringBuilder text = new StringBuilder();
		for (GraphConnection graphConnection : graphConnections) {
			text.append(graphConnection.getText()+ System.getProperty("line.separator"));
			if (i++ == 0) {
				graphConnection.setText(graphConnections.size() + ": connections");
				graphConnection.highlight();
			} else
				graphConnection.setVisible(false);
		}
		graphConnections.get(0)
				.setText(graphConnections.get(0).getText() + System.getProperty("line.separator") + text);
	}

	public String getName() {
		return componentData.getName();
	}

	public List<Extension> getExtensions() {
		return extensions;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isAtive() {
		return isAtive;
	}

	public void setAtive(boolean isAtive) {
		this.isAtive = isAtive;
	}

	public boolean hasConnection() {
		if (extensions == null || extensions.size() == 0) {
			return false;
		}
		return true;
	}

	public String getSymbolicName() {
		// TODO Auto-generated method stub
		return componentData.getSymbolicName();
	}

	public List<ExtensionPoint> getExtensionPoints() {
		return componentData.getExtensionPoints();
	}

}
