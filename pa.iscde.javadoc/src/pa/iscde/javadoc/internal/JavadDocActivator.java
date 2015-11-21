package pa.iscde.javadoc.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import pa.iscde.javadoc.service.JavaDocServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class JavadDocActivator implements BundleActivator {

	public static JavadDocActivator instance = null;

	//External Services
	private PidescoServices pidescoServices = null;
	private JavaEditorServices javaEditorServices = null;
	private ProjectBrowserServices projectBrowserServices = null;
	
	private ServiceRegistration<JavaDocServices> service;

	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		
		this.pidescoServices = getServiceReference(PidescoServices.class, context);
		this.javaEditorServices = getServiceReference(JavaEditorServices.class, context);
		this.projectBrowserServices = getServiceReference(ProjectBrowserServices.class, context);
		
		this.service = context.registerService(JavaDocServices.class, new JavaDocServicesImplementation(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		this.service.unregister();
	}

	public static JavadDocActivator getInstance() {
		return instance;
	}
	
	public PidescoServices getPidescoServices() {
		return pidescoServices;
	}

	public JavaEditorServices getJavaEditorServices() {
		return javaEditorServices;
	}

	public ProjectBrowserServices getProjectBrowserServices() {
		return projectBrowserServices;
	}

	private <T> T getServiceReference(Class<T> clazz, BundleContext context) {
		ServiceReference<T> ref = context.getServiceReference(clazz);
		return context.getService(ref);
	}
}