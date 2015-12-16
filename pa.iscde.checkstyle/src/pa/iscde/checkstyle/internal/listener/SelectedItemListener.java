package pa.iscde.checkstyle.internal.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pa.iscde.checkstyle.model.SharedModel;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;

/**
 * This class is used as a listener in order to perform some actions when the
 * elements of the project (project, packages and classes) from project browser
 * component.
 * 
 * For that end, we need to use the class {@link ProjectBrowserListener.Adapter}
 * However only the elements defined as classes (isClass) are used and the other
 * ones are ignored.
 *
 */
public class SelectedItemListener extends ProjectBrowserListener.Adapter {

	/**
	 * Use to initialize a list with only one position.
	 */
	private static final int ONE_ELEMENT = 1;

	/**
	 * This method will be used to perform some action when a element/elements
	 * are selected from project browser component.
	 * 
	 * @param elements
	 *            The elements that are selected from project browser component.
	 */
	@Override
	public void selectionChanged(Collection<SourceElement> elements) {
		SharedModel.getInstance().resetElements();

		final List<SourceElement> selectedElements = new ArrayList<SourceElement>(elements.size());
		for (SourceElement element : elements) {
			if (!selectedElements.contains(element)) {
				selectedElements.add(element);
			}
		}

		SharedModel.getInstance().addElements(selectedElements);
	}

	/**
	 * This method will be used to perform some action when a element is
	 * selected from project browser component.
	 * 
	 * @param element
	 *            The element that is selected to be opened in the Java Editor.
	 */
	@Override
	public void doubleClick(SourceElement element) {
		SharedModel.getInstance().resetElements();

		final List<SourceElement> selectedElements = new ArrayList<SourceElement>(ONE_ELEMENT);
		if (element.isClass() && !selectedElements.contains(element)) {
			selectedElements.add(element);
		}

		SharedModel.getInstance().addElements(selectedElements);
	}
}
