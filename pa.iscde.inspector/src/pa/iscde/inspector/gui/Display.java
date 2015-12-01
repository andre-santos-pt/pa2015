package pa.iscde.inspector.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pa.iscde.inspector.component.Component;
import pa.iscde.inspector.component.ComponentData;
import pa.iscde.inspector.internal.InspectorAtivator;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;

public class Display implements PidescoView {

	private List<ComponentDisign> componentDisigns;
	private PidescoServices service;
	
	public Display() {
//		service = InspectorAtivator.getInstance().getService();
	}

	public void init() {
		componentDisigns = new ArrayList<ComponentDisign>();
		List<ComponentData> components = Component.getAllAvailableComponents();

		for (ComponentData component : components) {
			componentDisigns.add(new ComponentDisign(component));
		}

	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new RowLayout(SWT.HORIZONTAL));
		Label label = new Label(viewArea, SWT.NONE);
		label.setImage(imageMap.get("97ada9ea-816d-11e5-9dc5-79f5d659e5a8.png"));
		Text text = new Text(viewArea, SWT.BORDER);
		text.setText("Hello world");

	}

}
