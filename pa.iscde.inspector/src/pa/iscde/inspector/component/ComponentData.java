package pa.iscde.inspector.component;

import java.util.List;

import org.osgi.framework.Bundle;

public interface ComponentData {
	
	String getName();
	List<Extension> getExtensions();
	List<ExtensionPoint> getExtensionPoints();
	List<String> getServices();
	String getSymbolicName();
	Bundle getBundle();

}
