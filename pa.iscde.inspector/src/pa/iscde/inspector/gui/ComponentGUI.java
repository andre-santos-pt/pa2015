package pa.iscde.inspector.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import pa.iscde.inspector.extensibility.IAction;
import pa.iscde.inspector.extensibility.TestExtensionPoint;


public class ComponentGUI {

	private Composite viewArea;
	private List<ComponentDisign> componentDisigns;
	private Collection<Composite> composites = new ArrayList<Composite>();
	Composite zestPanel;
	Composite configButtonPanel;
	Composite actionPanel;
	

	public ComponentGUI(Composite viewArea,List<ComponentDisign> componentDisigns) {
		this.viewArea = viewArea;
		this.componentDisigns = componentDisigns;
	}
	
	private void organizeLayout() {
		viewArea.setLayout(new GridLayout(1,false));
		configButtonPanel = new Composite(viewArea, SWT.NONE);
		configButtonPanel.setLayout(new RowLayout(SWT.VERTICAL | SWT.UP));
		zestPanel = new Composite(viewArea,SWT.NONE);
		zestPanel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		actionPanel = new Composite(viewArea,SWT.NONE);
		actionPanel.setLayout(new FillLayout());
		
		composites.add(zestPanel);
		composites.add(configButtonPanel);
		composites.add(actionPanel);

		
		
	}

	public void fillArea() {
		organizeLayout();
		final Graph graph = new Graph(zestPanel, SWT.NONE);
		graph.addSelectionListener(new GUISelectionAdapter());
		graph.setConnectionStyle(ZestStyles.CONNECTIONS_SOLID);
		drawNode(graph);
		drawConnections();
		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		final Button b = new Button(configButtonPanel,SWT.NONE);
		b.setText("Back");
		
		b.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (Composite composite :composites ) {
					composite.dispose();
				}
				InfoInit();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		addAtionButton();
		zestPanel.setLayout(new FillLayout());


	}
	
	private void addAtionButton(){
		TestExtensionPoint testExtensionPoint = new TestExtensionPoint();
		for (IAction actions : testExtensionPoint.getiActions()) {
			final Button b = new Button(actionPanel,SWT.NONE);
			b.setText(actions.Name());
		}
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
