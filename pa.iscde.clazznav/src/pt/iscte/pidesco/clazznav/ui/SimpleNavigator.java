package pt.iscte.pidesco.clazznav.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import pt.iscte.pidesco.clazznav.Activator;

public class SimpleNavigator extends AbstractNavigator implements NavigatorInterface {

	private Button previousButton;
	private Button afterButton;
	private Button graphicModeButton;
	private int index = -2;

	public SimpleNavigator(Composite composite){
		super(composite);
	}

	@Override
	public void build() {

		previousButton = new Button(getComposite(),  SWT.ARROW | SWT.LEFT);

		graphicModeButton = new Button(getComposite(), SWT.ARROW_UP);
		Image image = new Image(getComposite().getDisplay(), this.getClass().getResourceAsStream("/images/navigator_icon2.png"));
		graphicModeButton.setImage(image);

		afterButton = new Button(getComposite(),  SWT.ARROW | SWT.RIGHT);

		buttonListeners();

	}



	private void buttonListeners(){
		previousButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				System.out.println(AbstractNavigator.files.size());
				if(AbstractNavigator.files.size()>=2){
					File x =	AbstractNavigator.files.get(AbstractNavigator.files.size() + index);
					if(!AbstractNavigator.files.isEmpty()){
						if(!(AbstractNavigator.files.get(AbstractNavigator.files.size() + index + 1 ).getName().equals(x.getName()))){
							Activator.javaEditorService.openFile(x);
						}
					}
					index--;
				}
				System.out.println(index);
			}
		});

		afterButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub

			}
		});
	}



	@Override
	public void dispose() {
		previousButton.dispose();
		afterButton.dispose();
		graphicModeButton.dispose();

	}
	/**
	 * @return the previousButton
	 */
	public Button getPreviousButton() {
		return previousButton;
	}

	/**
	 * @param previousButton the previousButton to set
	 */
	public void setPreviousButton(Button previousButton) {
		this.previousButton = previousButton;
	}

	/**
	 * @return the afterButton
	 */
	public Button getAfterButton() {
		return afterButton;
	}

	/**
	 * @param afterButton the afterButton to set
	 */
	public void setAfterButton(Button afterButton) {
		this.afterButton = afterButton;
	}

	/**
	 * @return the graphicModeButton
	 */
	public Button getGraphicModeButton() {
		return graphicModeButton;
	}

	/**
	 * @param graphicModeButton the graphicModeButton to set
	 */
	public void setGraphicModeButton(Button graphicModeButton) {
		this.graphicModeButton = graphicModeButton;
	}

	public void setLayoutData(GridData data) {
		previousButton.setLayoutData(data);
		afterButton.setLayoutData(data);
		graphicModeButton.setLayoutData(data);

	}


}
