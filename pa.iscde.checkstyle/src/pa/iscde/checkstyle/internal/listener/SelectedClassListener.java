package pa.iscde.checkstyle.internal.listener;

import java.io.File;

import pa.iscde.checkstyle.internal.CheckStyleActivator;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

/**
 * This class is used as a listener in order to perform some actions when a
 * class is, for instance, opened, closed, saved from Java editor component.
 * 
 * For that end, we need to use the class {@link JavaEditorListener.Adapter}
 * 
 */
public class SelectedClassListener extends JavaEditorListener.Adapter {

	/**
	 * Holds a reference to Java editor services.
	 */
	private JavaEditorServices jeServices;

	/**
	 * Default constructor.
	 * 
	 * @param jeServices
	 *            The reference to Java editor services initialized in
	 *            {@link CheckStyleActivator}.
	 */
	public SelectedClassListener(JavaEditorServices jeServices) {
		this.jeServices = jeServices;
	}

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		jeServices.addAnnotation(file, AnnotationType.WARNING, "Teste", 179, 0);
	}
}
