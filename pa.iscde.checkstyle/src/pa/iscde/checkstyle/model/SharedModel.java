package pa.iscde.checkstyle.model;

import java.util.ArrayList;
import java.util.Collection;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public final class SharedModel {
	
	/**
	 * Used to enable/disable the console debug info.
	 */
	private static final boolean IS_DEBUG_ENABLED = true;

	private static final SharedModel INSTANCE = new SharedModel();

	private Collection<SourceElement> elements;

	private SharedModel(){
		elements = new ArrayList<SourceElement>();
	}
	
	public static SharedModel getInstance() {
		return INSTANCE;
	}

	public Collection<SourceElement> getElements() {
		return elements;
	}

	public void setElements(Collection<SourceElement> elements) {		
		this.elements = elements;
		debugInfo();
	}

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
