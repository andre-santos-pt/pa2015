package pa.iscde.inspector.extensibility;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

import pa.iscde.inspector.gui.ComponentDisign;

public interface IAction{
	
	String TabName();
	
	void actionComposite(Composite composite);

	void selectionChange(IActionComponent componentDisign);
	
	
	
}
