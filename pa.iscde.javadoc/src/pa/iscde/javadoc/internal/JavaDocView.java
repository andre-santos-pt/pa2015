package pa.iscde.javadoc.internal;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import pt.iscte.pidesco.extensibility.PidescoView;

public class JavaDocView implements PidescoView {

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		Browser browser = new Browser(viewArea, SWT.SIMPLE);
		browser.setText("<h1>JavaDoc View</h1>");
	}
}