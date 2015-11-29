package pa.iscde.javadoc.internal;

import java.io.File;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import pa.iscde.javadoc.generator.StringTemplateVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;

public class JavaDocView implements PidescoView {

	private static JavaDocView instance = null;

	private Composite viewArea;
	private Browser browser;

	private File lastParsedFile;

	public static JavaDocView getInstance() {
		return instance;
	}

	@Override
	public void createContents(final Composite viewArea, final Map<String, Image> imageMap) {

		instance = this;
		this.viewArea = viewArea;

		FillLayout fillLayout = new FillLayout();
		fillLayout.type = SWT.VERTICAL;
		viewArea.setLayout(fillLayout);

		this.browser = new Browser(viewArea, SWT.NONE);
		this.browser.setText("<h1>JavaDoc View</h1>");

		Button button = new Button(viewArea, SWT.SIMPLE);
		button.setText("Export JavaDoc");

		viewArea.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				instance = null;
			}
		});

		browser.addLocationListener(new LocationListener() {
			@Override
			public void changing(LocationEvent event) {

			}

			@Override
			public void changed(LocationEvent event) {
				if (event.location.contains("#") && event.location.contains("-")) {
					if (null != lastParsedFile
							&& lastParsedFile.equals(JavaDocServiceLocator.getJavaEditorService().getOpenedFile())) {
						String location[] = event.location.substring(event.location.indexOf("#") + 1).split("-");
						JavaDocServiceLocator.getJavaEditorService().selectText(lastParsedFile,
								Integer.valueOf(location[0]), Integer.valueOf(location[1]));
					}
				}
			}
		});

		File openedFile = null;
		if (null != (openedFile = JavaDocServiceLocator.getJavaEditorService().getOpenedFile())) {
			StringTemplateVisitor jDoc = new StringTemplateVisitor();
			JavaDocServiceLocator.getJavaEditorService().parseFile(openedFile, jDoc);
			this.browser.setText(jDoc.getSb().toString());
			lastParsedFile = openedFile;
		}

	}

	public static void closeView() {
		if (null != instance) {
			instance.viewArea.dispose();
		}
	}

}