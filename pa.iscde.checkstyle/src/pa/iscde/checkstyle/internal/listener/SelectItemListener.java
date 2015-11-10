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
 * . However only the elements defined as classes (isClass) are used and the
 * other ones are ignored.
 *
 */
public class SelectItemListener extends ProjectBrowserListener.Adapter {

	/**
	 * This collection holds the selected elements from project browser
	 * component for which the check style could be performed.
	 */
	private Collection<SourceElement> selectedElements;

	public SelectItemListener() {
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

		System.out.println("selectionChanged:\n");
		System.out.println(toString());
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

		System.out.println("doubleClick:\n");
		System.out.println(toString());
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
	 * 
	 * @param element
	 */
	private void addElement(SourceElement element) {
		if (element.isClass() && !this.selectedElements.contains(element)) {
			this.selectedElements.add(element);
		}
	}

	/**
	 * 
	 * @param elements
	 */
	private void addElements(Collection<SourceElement> elements) {
		for (SourceElement element : elements) {
			if (element.isClass() && !this.selectedElements.contains(element)) {
				this.selectedElements.add(element);
			}
		}
	}

	/**
	 * 
	 */
	private void resetSelectedElements() {
		this.selectedElements.clear();
	}
}
