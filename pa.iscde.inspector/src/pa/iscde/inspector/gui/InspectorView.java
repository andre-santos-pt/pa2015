package pa.iscde.inspector.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import pa.iscde.inspector.component.Component;
import pa.iscde.inspector.component.ComponentData;
import pt.iscte.pidesco.extensibility.PidescoView;

public class InspectorView implements PidescoView {

	private List<ComponentDisign> componentDisigns;
	private List<ComponentData> componentDatas;
	private Composite viewArea;

	public InspectorView() {
	}

	public void init() {
		componentDisigns = new ArrayList<ComponentDisign>();
		componentDatas = Component.getAllAvailableComponents();
		setExtensionPointOwnerDisign();
		new ComponentInfoView(viewArea, componentDisigns).fillInfoView();
	}

	private void setExtensionPointOwnerDisign() {
		for (ComponentData component : componentDatas) {
			ComponentDisign componentDisign = new ComponentDisign(component);
			componentDisign.setExtensionPointOwnerDesign();
			componentDisigns.add(componentDisign);
		}
	}

	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {
		this.viewArea = viewArea;
		init();
	}

}
