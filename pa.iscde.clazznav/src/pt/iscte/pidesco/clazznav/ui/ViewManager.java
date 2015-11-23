package pt.iscte.pidesco.clazznav.ui;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ViewManager {

	private Composite        composite;
	private GraphicNavigator graphNavigator;
	private SimpleNavigator  simpleNavigator;

	public ViewManager(Composite composite){
		this.setComposite(composite);

//		graphNavigator  = new GraphicNavigator(composite);
		graphNavigator = GraphicNavigator.getInstance(composite);
		graphNavigator.build();
		
		simpleNavigator = new SimpleNavigator(composite);
		simpleNavigator.build();
	}

	public void setup(){

		GridLayout layout = new GridLayout(3, true);
		composite.setLayout(layout);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 3;
		data.heightHint = 150;
		graphNavigator.getGraph().setLayoutData(data);

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.heightHint = 50;
		simpleNavigator.setLayoutData(data);

		changeAspectListener();
	}

	/*
	 * 
	 */
	private void changeAspectListener(){

		simpleNavigator.getGraphicModeButton().addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {

				if( graphNavigator.isEnabled()){
					graphNavigator.disable();
				}
				else{
					graphNavigator.enable();
				}

			}
		});
	}

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
