package pt.iscde.classdiagram.model;

import java.util.SortedSet;

import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * This class provides a set of helper methods to handle {@link SourceElement} related tasks.
 * @author joaocarias
 * @since 1.0  
 */
public class SourceElementTools {
	
	/**
	 * Searches a {@link SourceElement} in the SourceElement tree by it's fully qualified name
	 * @param rootElement the starting point of the search, typically the root node should be used.
	 * @param elementQalifiedName the fully qualified name of the element to search for.
	 * @return the {@link SourceElement} if it is found, or <code>null</code> otherwise. 
	 */
	public static SourceElement traverseNode(SourceElement rootElement, String elementQalifiedName) {
		SourceElement correctNode = null;
		if (isCorrectElement(rootElement, elementQalifiedName)) {
			correctNode = rootElement;
		} else {
			if (rootElement.isPackage()) {

				SortedSet<SourceElement> childNodes = ((PackageElement) rootElement).getChildren();
				for (SourceElement child : childNodes) {
					correctNode = traverseNode(child, elementQalifiedName);
					if (correctNode != null) {
						break;
					}
				}
			}
		}

		return correctNode;
	}

	private static boolean isCorrectElement(SourceElement element, String id) {
		String nameToRoot = getElementQualifiedName(element, "");
		return nameToRoot.contains(id);
	}


	private static String getElementQualifiedName(SourceElement element, String name) {
		if (name == null) {
			name = "";
		}
		if (element.getParent() == null) {
			return name;
		} else {
			String currentName = !"".equals(name) ? element.getName() + "." + name : element.getName();
			return getElementQualifiedName(element.getParent(), currentName);
		}
	}
}
