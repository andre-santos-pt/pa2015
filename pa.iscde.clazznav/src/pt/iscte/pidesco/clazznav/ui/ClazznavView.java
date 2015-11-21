package pt.iscte.pidesco.clazznav.ui;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * 
 * @author tiagocms
 *
 */
public class ClazznavView implements PidescoView{

	ViewManager viewManager;

	private JavaEditorServices jEditorServices;

	public ClazznavView() {
	}

	/**
	 * 
	 */
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		Composite child = new Composite(viewArea, SWT.NONE);

		viewManager = new ViewManager( child );
		viewManager.setup();
	}


	/**
	 * 
	 */
	private void dispose(){
		viewManager.disposeAll();
	}


}
