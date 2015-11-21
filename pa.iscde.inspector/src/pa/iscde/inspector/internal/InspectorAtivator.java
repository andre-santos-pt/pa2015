package pa.iscde.inspector.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoServices;

public class InspectorAtivator implements BundleActivator {

	private static InspectorAtivator instance;
	private PidescoServices service;
	private BundleContext context;
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		ServiceReference<PidescoServices> serviceReference = context.getServiceReference(PidescoServices.class);
		service = context.getService(serviceReference);
		this.context = context;
		
		
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		context = null;
		
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
