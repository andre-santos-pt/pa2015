package pt.iscte.pidesco.clazznav.ui;

import org.eclipse.swt.widgets.Composite;

public class AbstractNavigator {
	
	private Composite composite;
	
	public AbstractNavigator(Composite composite) {
		this.composite = composite;
	}

	@SuppressWarnings(value = { "unchecked" })
	public void dispose(){
		composite.dispose();
	}
	
	public Composite getComposite() {
		return composite;
	}
	
}
