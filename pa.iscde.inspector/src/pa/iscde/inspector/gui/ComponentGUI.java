package pa.iscde.inspector.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;


public class ComponentGUI {

	private Composite viewArea;
	private List<ComponentDisign> componentDisigns;

	public ComponentGUI(Composite viewArea,List<ComponentDisign> componentDisigns) {
		this.viewArea = viewArea;
		this.componentDisigns = componentDisigns;
	}
	
	public void fillArea() {
		final Graph graph = new Graph(viewArea, SWT.NONE);
		graph.addSelectionListener(new GUISelectionAdapter());
		graph.setConnectionStyle(ZestStyles.CONNECTIONS_SOLID);
		drawNode(graph);
		drawConnections();
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		final Button b = new Button(viewArea,SWT.NONE);
		b.setText("Back");
		
		b.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				graph.dispose();
				b.dispose();
				InfoInit();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});

	}

	protected void InfoInit() {
		new ComponentInfoView(viewArea, componentDisigns).fillInfoView();
		
	}

	private void drawConnections() {
		for (ComponentDisign componentDisign : componentDisigns) {
			if (componentDisign.hasConnection()) {
				componentDisign.drawConnections();
			}
		}
	}

	private void drawNode(Graph graph) {
		for (ComponentDisign componentDisign : componentDisigns) {
			componentDisign.drawNode(graph);
		}
	}
}
