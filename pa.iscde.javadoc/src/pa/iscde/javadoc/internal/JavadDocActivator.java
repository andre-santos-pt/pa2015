package pa.iscde.javadoc.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;

import pa.iscde.javadoc.service.JavaDocServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class JavadDocActivator implements BundleActivator {

	public static JavadDocActivator instance;

	// External Services
	private PidescoServices pidescoServices;

	// OSGi Services
	private LogService logService;

	private JavaEditorServices javaEditorServices;
	private JavaEditorListener javaEditorListener;

	private ProjectBrowserServices projectBrowserServices;
	private ProjectBrowserListener projectBrowserListener;

	private ServiceRegistration<JavaDocServices> service;

	@Override
	public void start(final BundleContext context) throws Exception {

		instance = this;

		logService = getServiceReference(LogService.class, context);
		if (logService == null) {
			System.out.println("Unable to get logger service. Javadoc plugin will not be loaded.");
			return;
		}

		this.pidescoServices = getServiceReference(PidescoServices.class, context);

		this.javaEditorServices = getServiceReference(JavaEditorServices.class, context);
		if (null != javaEditorServices) {
			javaEditorServices.addListener(javaEditorListener = new JavaEditorListenerImpl(logService));
		}

		this.projectBrowserServices = getServiceReference(ProjectBrowserServices.class, context);
		if (null != projectBrowserServices) {
			projectBrowserServices.addListener(projectBrowserListener = new ProjectBrowserListenerImpl(logService));
		}

		this.service = context.registerService(JavaDocServices.class, new JavaDocServicesImplementation(logService),
				null);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {

		instance = null;

		if (javaEditorServices != null && null != javaEditorListener) {
			javaEditorServices.removeListener(javaEditorListener);
		}

		if (projectBrowserListener != null && null != projectBrowserListener) {
			projectBrowserServices.removeListener(projectBrowserListener);
		}

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

	private <T> T getServiceReference(final Class<T> clazz, final BundleContext context) {
		T service = null;
		ServiceReference<T> ref = context.getServiceReference(clazz);
		if (null == ref) {
			logService.log(LogService.LOG_ERROR, "Unable to get service reference:" + clazz.getName());
		} else {
			service = context.getService(ref);
			if (null == service) {
				logService.log(LogService.LOG_ERROR, "Unable to get service:" + clazz.getName());
			}
		}
		return service;
	}

}