package pt.iscte.pidesco.clazznav.ui;

import java.rmi.activation.Activator;
import java.util.ArrayList;

import org.eclipse.draw2d.GridData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

/**
 * 
 * @author tiagocms
 *
 */
public class GraphicNavigator extends AbstractNavigator{


	private boolean enabled = false;

	private Graph graph;
	private ArrayList<GraphNode> nodes = new ArrayList<>(); //Trocar por uma collection do guava

	//Constants
	private int style = SWT.NONE;

	public GraphicNavigator(Composite composite){
		super(composite);
		graph = new Graph(composite, style);
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		
		CustomNode test = new CustomNode(graph);
		test.dummyTester(Activator.class);
		nodes.add(test);

		graphLisener();

		graph.setVisible(enabled);
	}


	public void enable(){
		setEnabled(true);
		graph.setVisible(true);
		getComposite().redraw(); //need it??
	}

	public void disable(){
		setEnabled(false);
		graph.setVisible(false);
	}




	/**
	 * 
	 */
	private void graphLisener(){
		graph.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				CustomNode n = new CustomNode(graph);
				n.dummyTester(GraphConnection.class);

				GraphConnection c = new GraphConnection(graph, SWT.NONE, nodes.get(nodes.size() -1 ), n	);
				nodes.add(n);
				graph.redraw();
				graph.update();
			}
		});
	}

	/*
	 * 
	 */

	/**
	 * 
	 * @return
	 */
	public Graph getGraph() {
		return graph;
	}



	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}


	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
