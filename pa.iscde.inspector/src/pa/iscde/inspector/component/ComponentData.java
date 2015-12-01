package pa.iscde.inspector.component;

import java.util.List;

public interface ComponentData {
	
	String getName();
	List<Extension> getExtensions();
	List<ExtensionPoint> getExtensionPoints();
	List<String> getServices();
	String getSymbolicName();

}
