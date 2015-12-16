package pa.iscde.inspector.extensibility;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

public class TestExtensionPoint {

	IExtensionRegistry extRegistry = Platform.getExtensionRegistry();

	IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.inspector.inspectorAction");

	Collection<IAction> iActions = new ArrayList<IAction>();
	

	public TestExtensionPoint() {
		init();
	}

	void init() {
		IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for (IConfigurationElement c : confElements) {
				String s = c.getAttribute("name");
				try {
					iActions.add((IAction) c.createExecutableExtension("class"));
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	public Collection<IAction> getiActions() {
		return iActions;
	}

}
