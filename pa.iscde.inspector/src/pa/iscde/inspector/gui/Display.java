package pa.iscde.inspector.gui;


import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pt.iscte.pidesco.extensibility.PidescoView;

public class Display implements PidescoView {


	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label label = new Label(viewArea, SWT.NONE);
		label.setImage(imageMap.get("97ada9ea-816d-11e5-9dc5-79f5d659e5a8.png"));
		Text text = new Text(viewArea, SWT.BORDER);
		text.setText("Hello world");

	}


}
