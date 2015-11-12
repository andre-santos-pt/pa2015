package pa.iscde.checkstyle.internal.listener;

import java.io.File;

import pa.iscde.checkstyle.internal.CheckStyleActivator;
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
	 * Used to enable/disable the console debug info.
	 */
	private static final boolean IS_DEBUG_ENABLED = true;
	
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
	public void fileSaved(File file) {
		//System.out.println("SelectedClassListener" + file.getName());
		// jeServices.addAnnotation(file, AnnotationType.ERROR, "Teste", 179,
		// 56);
		//jeServices.insertLine(file, "//Teste", 1);
	}

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		/*
		 * System.out.println("File:" + file.getName());
		 * System.out.println("Text:" + text); System.out.println("Offset:" +
		 * offset); System.out.println("Length:" + length);
		 */
	}

	@Override
	public void fileOpened(File file) {
		// System.out.println("SelectedClassListener" + file.getName());
	}
}
