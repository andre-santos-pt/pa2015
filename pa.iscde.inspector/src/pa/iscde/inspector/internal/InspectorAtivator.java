package pa.iscde.inspector.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

import com.google.common.util.concurrent.Service;

import pt.iscte.pidesco.extensibility.PidescoServices;

public class InspectorAtivator implements BundleActivator {

	private static InspectorAtivator instance;
	private PidescoServices service;
	private BundleContext context;
	
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		System.out.println("started");
		ServiceReference<PidescoServices> serviceReference = context.getServiceReference(PidescoServices.class);
		service = context.getService(serviceReference);
		this.context = context;
		
		ServiceReference<?>[] allServiceReferences = context.getAllServiceReferences(null, null);
		for (ServiceReference<?> serviceReference2 : allServiceReferences) {
			Object service2 = context.getService(serviceReference2);
			if(service2.getClass().getInterfaces().length != 0){
				System.out.println(service2.getClass().getInterfaces()[0].getName());
			}
		}
				
		context.addBundleListener(new BundleListener() {
			
			@Override
			public void bundleChanged(BundleEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
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
