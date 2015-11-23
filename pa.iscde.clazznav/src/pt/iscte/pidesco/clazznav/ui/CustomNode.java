package pt.iscte.pidesco.clazznav.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;

public class CustomNode extends GraphNode {


	private final int HEIGHT = 40;
	private final double WIDTH_FACTOR = 5.0;
	
	

	public CustomNode(IContainer graphModel, File file) {
		super(graphModel, SWT.NONE);
		setup(graphModel, file);
	}

	/**
	 * 
	 * @param file
	 */
	private void setup(IContainer graphModel, File file){
		Display display = graphModel.getGraph().getDisplay();

		Color color  = new Color (display, 255, 255, 255);
//		Color color  = new Color (display, 190, 180, 150);
		setBackgroundColor(color);
		
	
		
		String fileName = file.getName();
		Font font = new Font(display, new FontData( "Arial", 7, SWT.NORMAL));
		setFont(font);
		setText(fileName);
		setSize(fileName.length() * WIDTH_FACTOR , HEIGHT);
		
	}

}
