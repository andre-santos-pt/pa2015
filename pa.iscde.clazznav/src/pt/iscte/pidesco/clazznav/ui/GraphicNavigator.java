package pt.iscte.pidesco.clazznav.ui;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.draw2d.GridData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import pt.iscte.pidesco.clazznav.Activator;

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
		
		nodes.add(new CustomNode(graph));

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
				   File f = Activator.editor.getOpenedFile();
	               System.out.println(f.getName());
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
