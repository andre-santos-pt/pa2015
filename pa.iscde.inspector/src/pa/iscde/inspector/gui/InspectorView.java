package pa.iscde.inspector.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import pa.iscde.inspector.component.ComponentData;
import pa.iscde.inspector.internal.InspectorAtivator;
import pt.iscte.pidesco.extensibility.PidescoView;

public class InspectorView implements PidescoView {

	private List<ComponentDisign> componentDisigns;
	private HashMap<String, ComponentData> componentDatas;
	private Composite viewArea;

	public void init() {
		componentDisigns = new ArrayList<ComponentDisign>();
		componentDatas = InspectorAtivator.getInstance().getBundlemap();
		setExtensionPointOwnerDisign();
		new ComponentInfoView(viewArea, componentDisigns).fillInfoView();
	}

	private void setExtensionPointOwnerDisign() {
		for (Entry<String, ComponentData> component : componentDatas.entrySet()) {
			ComponentDisign componentDisign = new ComponentDisign(component.getValue());
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
