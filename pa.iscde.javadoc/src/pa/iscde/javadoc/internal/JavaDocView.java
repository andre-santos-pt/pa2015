package pa.iscde.javadoc.internal;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.extensibility.PidescoView;

public class JavaDocView implements PidescoView {
	
	private static JavaDocView instance = null;
	private Browser browser = null;
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		instance = this;
		
		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		viewArea.setLayout(fillLayout);
		
		browser = new Browser(viewArea, SWT.NONE);
		browser.setText("<h1>JavaDoc View</h1>");
		
		Button button = new Button(viewArea, SWT.SIMPLE);
		button.setText("Export JavaDoc");
	}
	
	public static JavaDocView getInstance(){
		return instance;
	}
}