package pt.iscte.pidesco.clazznav.ui;


import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ViewManager {

	private Composite        composite;
	private GraphicNavigator graphNavigator;
	private SimpleNavigator  simpleNavigator;

	public ViewManager(Composite composite){
		this.composite = composite;

		graphNavigator = GraphicNavigator.getInstance(composite);
		
		
		simpleNavigator = SimpleNavigator.getInstance(composite);
	
	}

	/**
	 * 
	 */
	public void setup(){
		GridLayout layout = new GridLayout(3, true);
		composite.setLayout(layout);	
		graphNavigator.build();
		simpleNavigator.build();
	}

	/**
	 * 
	 */
	public void disposeAll() {
		graphNavigator.dispose();
		simpleNavigator.dispose();
	}

	/**
	 * @return the composite
	 */
	public Composite getComposite() {
		return composite;
	}

	/**
	 * @param composite the composite to set
	 */
	public void setComposite(Composite composite) {
		this.composite = composite;
	}

}
