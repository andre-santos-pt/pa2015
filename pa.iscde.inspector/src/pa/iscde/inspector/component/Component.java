package pa.iscde.inspector.component;

import java.util.List;

import org.osgi.framework.BundleContext;

public class Component {
	
	private String plugin_name;
	private List<Component> extension_Point;
	private List<Component> extension;
	private boolean active;
	private BundleContext activator;
	
	
	public String getPlugin_name() {
		return plugin_name;
	}
	public void setPlugin_name(String plugin_name) {
		this.plugin_name = plugin_name;
	}
	public List<Component> getExtension_Point() {
		return extension_Point;
	}
	public void setExtension_Point(List<Component> extension_Point) {
		this.extension_Point = extension_Point;
	}
	public List<Component> getExtension() {
		return extension;
	}
	public void setExtension(List<Component> extension) {
		this.extension = extension;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public BundleContext getActivator() {
		return activator;
	}
	public void setActivator(BundleContext activator) {
		this.activator = activator;
	}
	
	
	
}
