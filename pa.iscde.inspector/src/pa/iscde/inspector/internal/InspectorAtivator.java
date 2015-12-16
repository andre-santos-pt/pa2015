package pa.iscde.inspector.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

import com.google.common.util.concurrent.Service;

import pa.iscde.inspector.component.Component;
import pa.iscde.inspector.component.ComponentData;
import pa.iscde.inspector.component.Extension;
import pa.iscde.inspector.component.ExtensionPoint;
import pa.iscde.inspector.gui.ComponentDisign;
import pt.iscte.pidesco.extensibility.PidescoServices;

public class InspectorAtivator implements BundleActivator {

	private static InspectorAtivator instance;
	private PidescoServices service;
	private BundleContext context;
	private HashMap<String, ComponentData> bundlemap;
	private HashMap<String, ComponentDisign> bundleDesignMap;

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		addBundles();
		createBlundleDisgnMap();
	}

	private void createBlundleDisgnMap() {
		bundleDesignMap = new HashMap<String, ComponentDisign>();
		for (Entry<String, ComponentData> entry : bundlemap.entrySet()) {
			bundleDesignMap.put(entry.getKey(), new ComponentDisign(entry.getValue()));  
		}
		context.addBundleListener(new BundleChange(bundleDesignMap));
		
	}

	private void addBundles() {
		Bundle[] bundles = context.getBundles();
		bundlemap = Component.getAllAvailableComponents();
		for (int i = 0; i < bundles.length; i++) {
			String key = bundles[i].getSymbolicName();
			if (bundlemap.containsKey(key)) {
				((Component) bundlemap.get(key)).setBundle(bundles[i]);
			}
		}

	}

	public HashMap<String, ComponentDisign> getBundleDesignMap() {
		return bundleDesignMap;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		context = null;

	}

	public HashMap<String, ComponentData> getBundlemap() {
		return bundlemap;
	}

	public BundleContext getContext() {
		return context;
	}

	public static InspectorAtivator getInstance() {
		return instance;
	}

	public PidescoServices getService() {
		return service;
	}

}
