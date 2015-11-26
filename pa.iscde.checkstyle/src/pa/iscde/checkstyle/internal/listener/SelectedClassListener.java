package pa.iscde.checkstyle.internal.listener;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import pa.iscde.checkstyle.converter.AnnotationConverter;
import pa.iscde.checkstyle.internal.CheckStyleActivator;
import pa.iscde.checkstyle.model.SharedModel;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;
import pa.iscde.checkstyle.model.ViolationModelProvider;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.ClassElement;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * This class is used as a listener in order to perform some actions when a
 * class is selected, for instance, opened, closed, saved from Java editor
 * component.
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
		final List<Violation> violations = ViolationModelProvider.getInstance().getViolations();

		if (violations == null || violations.isEmpty()) {
			SharedModel.getInstance().resetElements();

			final PackageElement packageElement = new PackageElement(null, file.getName(), file);
			final ClassElement sourceElement = new ClassElement(packageElement, file);

			SharedModel.getInstance().addElement(sourceElement);
		}

		for (Violation violation : violations) {
			final List<ViolationDetail> details = violation.getDetails();
			for (ViolationDetail detail : details) {
				if (detail.getResource().equals(file.getName())) {
					jeServices.addAnnotation(file, AnnotationConverter.convert(violation.getSeverity()),
							detail.getMessage(), detail.getOffset(), 0);
				}
			}
		}
	}

	@Override
	public void fileClosed(File file) {
		final List<SourceElement> elements = SharedModel.getInstance().getElements();

		if (elements == null || elements.size() == 0) {
			return;
		}

		final Iterator<SourceElement> iterator = elements.iterator();
		while (iterator.hasNext()) {
			final SourceElement element = iterator.next();
			if (element.getFile().equals(file)) {
				iterator.remove();
				break;
			}
		}
	}
}
