package pt.iscte.pidesco.clazznav.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Text;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;

import pt.iscte.pidesco.clazznav.utils.FilesUtil;

public class CustomNode extends GraphNode {


	private double HEIGHT = 20;
	private static int STYLE = SWT.NONE;

	public CustomNode(IContainer graphModel) {
		super(graphModel, STYLE);
	}

	public void dummyTester(Class<?>  clazz){
		String clazzName = clazz.getName();
		
		setText(clazzName);
		setSize(clazzName.length() * 10, HEIGHT);
	}

}
