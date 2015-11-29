package pa.iscde.javadoc.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.log.LogService;

import pa.iscde.javadoc.service.JavaDocServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class JavaDocActivator implements BundleActivator {

	public static JavaDocActivator instance;

	// OSGi Services
	private LogService logService;

	private JavaEditorListener javaEditorListener;

	private ProjectBrowserListener projectBrowserListener;

	private ServiceRegistration<JavaDocServices> service;

	private JavaEditorServices javaEditorServices;

	private ProjectBrowserServices projectBrowserServices;

	@Override
	public void start(final BundleContext context) throws Exception {

		instance = this;

		JavaDocServiceLocator.initialize(context);

		this.javaEditorServices = JavaDocServiceLocator.getJavaEditorService();
		if (null != javaEditorServices) {
			javaEditorServices.addListener(javaEditorListener = new JavaEditorListenerImpl(logService));
		}

		this.projectBrowserServices = JavaDocServiceLocator.getProjectBrowserService();
		if (null != projectBrowserServices) {
			projectBrowserServices.addListener(projectBrowserListener = new ProjectBrowserListenerImpl(logService));
		}

		JavaDocServicesImplementation jDocServiceImpl = new JavaDocServicesImplementation(logService);
		this.service = context.registerService(JavaDocServices.class, jDocServiceImpl, null);
		JavaDocServiceLocator.setService(JavaDocServices.class, jDocServiceImpl);

	}

	@Override
	public void stop(final BundleContext context) throws Exception {

		instance = null;


		if (javaEditorServices != null && null != javaEditorListener) {
			javaEditorServices.removeListener(javaEditorListener);
		}

		if (projectBrowserServices != null && null != projectBrowserListener) {
			projectBrowserServices.removeListener(projectBrowserListener);
		}

		this.service.unregister();
		
		JavaDocServiceLocator.deinitialize();
	}

	public static JavaDocActivator getInstance() {
		return instance;
	}

}
