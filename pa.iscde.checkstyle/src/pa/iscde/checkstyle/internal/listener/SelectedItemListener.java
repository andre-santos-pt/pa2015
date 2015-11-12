package pa.iscde.checkstyle.internal.listener;

import java.util.ArrayList;
import java.util.Collection;

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
	 * Used to enable/disable the console debug info.
	 */
	private static final boolean IS_DEBUG_ENABLED = true;

	/**
	 * This collection holds the selected elements from project browser
	 * component for which the check style could be performed.
	 */
	private Collection<SourceElement> selectedElements;

	/**
	 * Default constructor.s
	 */
	public SelectedItemListener() {
		this.selectedElements = new ArrayList<SourceElement>();
	}

	/**
	 * This method will be used to perform some action when a element/elements
	 * are selected from project browser component.
	 * 
	 * @param elements
	 *            The elements that are selected from project browser component.
	 */
	@Override
	public void selectionChanged(Collection<SourceElement> elements) {
		resetSelectedElements();
		addElements(elements);

		debugInfo("SelectedItemListener::selectionChanged:");
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
		resetSelectedElements();
		addElement(element);

		debugInfo("SelectedItemListener::doubleClick:");
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (SourceElement element : selectedElements) {
			builder.append(element);
			builder.append("\n");
		}
		return builder.toString();
	}

	/**
	 * This method is used to add one selected element into the collection of
	 * selected elements.
	 * 
	 * @param element
	 *            The selected element to be added.
	 */
	private void addElement(SourceElement element) {
		if (element.isClass() && !this.selectedElements.contains(element)) {
			this.selectedElements.add(element);
		}
	}

	/**
	 * This method is used to add several selected element into the collection
	 * of selected elements.
	 * 
	 * @param elements
	 *            The selected elements to be added.
	 */
	private void addElements(Collection<SourceElement> elements) {
		for (SourceElement element : elements) {
			if (element.isClass() && !this.selectedElements.contains(element)) {
				this.selectedElements.add(element);
			}
		}
	}

	/**
	 * This method is used to reinitialize the collection of selected elements
	 * in order to avoid to have up-to-date records.
	 */
	private void resetSelectedElements() {
		this.selectedElements.clear();
	}

	/**
	 * This method is an auxiliary method used to write in the console some
	 * debug information.
	 * 
	 * @param info
	 *            The info to be written in the console.
	 */
	private void debugInfo(String info) {
		if (IS_DEBUG_ENABLED) {
			System.out.println(info);
			System.out.println(toString());
		}
	}
}
