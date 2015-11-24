package pa.iscde.checkstyle.model;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * This class is used to hold the class elements selected from project browser
 * and java editor components, which are used by check style manager to
 * calculate potential violations.
 */
public final class SharedModel {

	/**
	 * Used to enable/disable the console debug info.
	 */
	private static final boolean IS_DEBUG_ENABLED = false;

	/**
	 * The singleton instance of this class.
	 */
	private static final SharedModel INSTANCE = new SharedModel();

	/**
	 * Holds the class elements selected from project browser and java editor
	 * components
	 */
	private List<SourceElement> elements;

	/**
	 * Default constructor.
	 */
	private SharedModel() {
		elements = new ArrayList<SourceElement>();
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return Singleton instance of this class.
	 */
	public static SharedModel getInstance() {
		return INSTANCE;
	}

	/**
	 * Return the list of selected class elements on which the check style
	 * process will be performed.
	 * 
	 * @return
	 */
	public List<SourceElement> getElements() {
		return this.elements;
	}

	/**
	 * This method is used to add a list of class elements.
	 * 
	 * @param elements
	 *            The class elements to be added.
	 */
	public void addElements(List<SourceElement> elements) {
		this.elements.addAll(elements);
		debugInfo();
	}

	/**
	 * This method is used to add one class element.
	 * 
	 * @param element
	 *            The class element to be added.
	 */
	public void addElement(SourceElement element) {
		this.elements.add(element);
		debugInfo();
	}

	/**
	 * This method is used to reset the selected class elements.
	 */
	public void resetElements() {
		this.elements.clear();
		debugInfo();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (SourceElement element : elements) {
			builder.append(element);
			builder.append("\n");
		}
		return builder.toString();
	}

	/**
	 * This method is an auxiliary method used to write in the console some
	 * debug information.
	 * 
	 */
	private void debugInfo() {
		if (IS_DEBUG_ENABLED) {
			System.out.println(toString());
		}
	}
}
