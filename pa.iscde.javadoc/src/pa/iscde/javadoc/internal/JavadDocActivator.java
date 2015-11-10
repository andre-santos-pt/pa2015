package pa.iscde.javadoc.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pa.iscde.javadoc.service.JavaDocServices;
import pt.iscte.pidesco.extensibility.PidescoServices;

public class JavadDocActivator implements BundleActivator {

	private PidescoServices pidescoServices;
	private ServiceRegistration<JavaDocServices> service;
	
	@Override
	public void start(BundleContext context) throws Exception {
		this.service = context.registerService(JavaDocServices.class, new JavaDocServicesImplementation(), null);
		
		ServiceReference<PidescoServices> ref = context.getServiceReference(PidescoServices.class);
		this.pidescoServices = context.getService(ref);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		this.service.unregister();
	}
}