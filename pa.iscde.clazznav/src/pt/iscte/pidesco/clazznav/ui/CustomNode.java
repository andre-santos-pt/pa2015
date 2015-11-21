package pt.iscte.pidesco.clazznav.ui;

import org.eclipse.swt.SWT;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;

public class CustomNode extends GraphNode {


	private int WIDTH = 20;
	private int HEIGHT = 20;
	private static int STYLE = SWT.NONE;

	public CustomNode(IContainer graphModel) {
		super(graphModel, STYLE);
		setup();
	}

	private void setup(){
		setSize(WIDTH, HEIGHT);		
	}

}
