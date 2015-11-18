package pt.iscte.pidesco.clazznav.ui;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * 
 * @author tiagocms
 *
 */
public class clazznavView implements PidescoView{

	private Label label;

	private Button buttonLeft;
	private Button buttonRight;

	private JavaEditorServices jEditorServices;

	public clazznavView() {
	}

	/**
	 * 
	 */
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {


		Composite child = new Composite(viewArea, SWT.NONE);

		GraphBuilder graphBuilder = new GraphBuilder(child);;


		//		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		//		layout.wrap = true;
		//		layout.fill = true;
		//		layout.justify = false;
		//		
		//		label = new Label(child, SWT.None);
		//		label.setText("TESTE");
		//		
		//		RowData data;
		//
		//		buttonLeft = new Button(child, SWT.ARROW);
		//		buttonLeft.setAlignment(SWT.LEFT);
		//		data = new RowData();
		//		data.width = Parameters.BUTTON_HEIGHT;
		//		data.height = Parameters.BUTTON_HEIGHT;
		//		buttonLeft.setLayoutData(data);
		//
		//		buttonRight = new Button(child, SWT.ARROW);
		//		buttonRight.setAlignment(SWT.RIGHT);
		//		buttonRight.setLayoutData(data);
		//
		//		child.setLayout(layout);
		//		child.setSize(100, 100);
		//		child.setParent(viewArea);


		//
		//		GridLayout gridLayout = new GridLayout();
		//		gridLayout.numColumns = 2;
		//		gridLayout.makeColumnsEqualWidth = true;
		//
		//		child.setLayout(gridLayout);
		//
		//		Button button1 = new Button(child, SWT.PUSH);
		//		button1.setText("button1");
		//
		//		button1.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
		//
		//		Button button3 = new Button(child, SWT.PUSH);
		//		button3.setText("3");
		//		button3.setSize(100, 100);
		//		GridData a =  new GridData(GridData.VERTICAL_ALIGN_CENTER);
		//		a.minimumHeight = 50;
		//		a.verticalSpan = 50;
		//		a.heightHint = 50;
		//		a.widthHint = 50;
		//
		//		button3.setLayoutData(a);
		//
		//		Button button2 = new Button(child, SWT.PUSH);
		//		button2.setText("button #2");  
		//		GridData gridData = new GridData(GridData.VERTICAL_ALIGN_END);
		//		gridData.horizontalSpan = 2;
		//		gridData.horizontalAlignment = GridData.FILL;
		//		button2.setLayoutData(gridData);
	}

	/**
	 * 
	 */
	public void buttonListeners(){
		buttonLeft.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO

			}
		});

		buttonRight.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO 

			}
		});

	}

	/**
	 * 
	 */
	private void dispose(){
		buttonLeft.dispose();
		buttonRight.dispose();
	}

	/**
	 * 
	 */
	public Button getButtonLeft() {
		return buttonLeft;
	}

	/**
	 * 
	 */
	public Button getButtonRight() {
		return buttonRight;
	}


}
