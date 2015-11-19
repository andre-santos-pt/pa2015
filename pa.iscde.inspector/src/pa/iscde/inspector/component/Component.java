package pa.iscde.inspector.component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleActivator;

public class Component {

	private String Name;
	private List<Extension> extension;
	private List<ExtensionPoint> extensionPoint;
	private List<String> requiredBundle;
	private String ativator;
	private boolean state;

	public static List<Component> getAllAvailableComponents() {
		List<FilesToRead> filesToReadPaths = FileReader.getFilesPaths();
		List<Component> components = new ArrayList<Component>();
		for (FilesToRead path : filesToReadPaths) {
			Component comp = new Component();
			ManifestParser manifestParser = new ManifestParser().readFile(new File(path.getManifestPath()));
			PluginXmlParser pluginXmlParser = new PluginXmlParser().ReadFile(new File(path.getPluginXmlPath()));

			comp.Name = manifestParser.getName();
			comp.requiredBundle = manifestParser.getRequiredBundle();
			comp.ativator = manifestParser.getAtivator();
			comp.extension = pluginXmlParser.getExtension();
			comp.extensionPoint = pluginXmlParser.getExtensionPoint();

			components.add(comp);
		}
		return components;
	}

	public Component(String manifestPath, String pluginXmlPath) {
		ManifestParser parser = new ManifestParser().readFile(new File(manifestPath));
		PluginXmlParser pluginXmlParser = new PluginXmlParser().ReadFile(new File(manifestPath));
		extension = pluginXmlParser.getExtension();
		extensionPoint = pluginXmlParser.getExtensionPoint();
		Name = parser.getName();
		requiredBundle = parser.getRequiredBundle();
		ativator = parser.getAtivator();
	}

	public Component() {
	}

	public List<Extension> getExtension() {
		return extension;
	}

	public String getName() {
		return Name;
	}

	public List<ExtensionPoint> getExtensionPoint() {
		return extensionPoint;
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

	public static void main(String[] args) {
		List<Component> comp = Component.getAllAvailableComponents();

		for (Component component : comp) {
			System.out.println("Name: " + component.Name + " ");
			System.out.println("Ativator: "  + component.ativator + " ");

			for (Extension extension : component.extension) {
				System.out.println(extension + " ");
			}

			for (ExtensionPoint extensionPoint : component.extensionPoint) {
				System.out.println(extensionPoint + " ");
			}

			for (String req : component.requiredBundle) {
				System.out.println("Required Bundle: " + req);
			}
			System.out.println("\n");
		}
	}
}
