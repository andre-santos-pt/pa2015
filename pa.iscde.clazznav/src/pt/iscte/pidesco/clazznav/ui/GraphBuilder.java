package pt.iscte.pidesco.clazznav.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.internal.ZoomManager;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

/**
 * 
 * @author tiagocms
 *
 */
public class GraphBuilder {

	Graph graph;
	GraphNode node ;

	int style = SWT.NONE;

	public GraphBuilder(Composite composite){

		graph = new Graph(composite, style);
		graph.setSize(400, 200);
		graphLisener();
		node = new GraphNode(graph, SWT.None, "TESTTTTT2");
		node = new GraphNode(graph, SWT.None, "TESTTTTT3");
		node = new GraphNode(graph, SWT.None, "TESTTTTT4");
		node = new GraphNode(graph, SWT.None, "TESTTTTT5");
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);

		GraphViewer a = new GraphViewer(graph, SWT.NONE);

		a.getGraphControl().addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {
				// TODO implementar o zoom
			}
		});
	}

	/**
	 * 
	 */
	private void graphLisener(){
		graph.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				System.out.println(((Graph) e.widget).getSelection());
			}
		});
	}


	/**
	 * 
	 * @return
	 */
	public Graph getGraph() {
		return graph;
	}

}
