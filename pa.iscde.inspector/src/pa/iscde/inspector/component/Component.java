package pa.iscde.inspector.component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.osgi.framework.Bundle;

public class Component implements ComponentData{

	private String Name;
	private List<Extension> extensions;
	private List<ExtensionPoint> extensionPoints;
	private List<String> requiredBundle;
	private String ativator;
	private boolean state;
	private List<String> services;
	private String symbolicName;
	private Bundle bundle;
	private static List<ExtensionPoint> EXTPOINTS = new ArrayList<ExtensionPoint>();

	public static HashMap<String,ComponentData> getAllAvailableComponents() {
		List<FilesToRead> filesToReadPaths = FileReader.getFilesPaths();
		HashMap<String,ComponentData> components = new HashMap<String,ComponentData>();
		for (FilesToRead path : filesToReadPaths) {
			Component comp = new Component();
			ManifestParser manifestParser = new ManifestParser().readFile(new File(path.getManifestPath()));

			comp.Name = manifestParser.getName();
			comp.requiredBundle = manifestParser.getRequiredBundle();
			comp.ativator = manifestParser.getAtivator();
			comp.symbolicName = manifestParser.getSymbolicName();
			PluginXmlParser pluginXmlParser = new PluginXmlParser().ReadFile(new File(path.getPluginXmlPath()), comp);

			comp.extensions = pluginXmlParser.getExtension();
			comp.extensionPoints = pluginXmlParser.getExtensionPoint();
			
			EXTPOINTS.addAll(comp.extensionPoints);

			components.put(manifestParser.getSymbolicName(),comp);
		}
		for (Entry<String, ComponentData> component : components.entrySet()) {
			for (Extension extension : component.getValue().getExtensions()) {
				extension.findConnections(EXTPOINTS);
			}
		}
		return components;
	}

	public Component(String manifestPath, String pluginXmlPath) {
		ManifestParser parser = new ManifestParser().readFile(new File(manifestPath));
		PluginXmlParser pluginXmlParser = new PluginXmlParser().ReadFile(new File(manifestPath), this);
		extensions = pluginXmlParser.getExtension();
		extensionPoints = pluginXmlParser.getExtensionPoint();
		Name = parser.getName();
		requiredBundle = parser.getRequiredBundle();
		ativator = parser.getAtivator();
	}

	public Component() {
	}
	@Override
	public List<Extension> getExtensions() {
		return extensions;
	}
	@Override
	public String getName() {
		return Name;
	}
	@Override
	public List<ExtensionPoint> getExtensionPoints() {
		return Collections.unmodifiableList(extensionPoints);
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public List<String> getRequiredBundle() {
		return requiredBundle;
	}

//	public static void main(String[] args) {
//		List<ComponentData> comp = Component.getAllAvailableComponents();
//
//		for (ComponentData component : comp) {
//			System.out.println("Name: " + component.getName() + " ");
//			System.out.println("Ativator: " + ((Component)component).ativator + " ");
//
//			for (Extension extension : component.getExtensions()) {
//				System.out.println(extension + " ");
//			}
//
//			for (ExtensionPoint extensionPoint : component.getExtensionPoints()) {
//				System.out.println(extensionPoint + " ");
//			}
//
//			for (String req : ((Component)component).requiredBundle) {
//				System.out.println("Required Bundle: " + req);
//			}
//			System.out.println("\n");
//		}
//	}

	@Override
	public List<String> getServices() {
		return services;
	}

	@Override
	public String getSymbolicName() {
		
		return symbolicName;
	}

	@Override
	public Bundle getBundle() {
		
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
		
	}

}
