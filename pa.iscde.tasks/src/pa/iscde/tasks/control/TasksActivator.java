package pa.iscde.tasks.control;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TasksActivator implements BundleActivator {

	private static PidescoServices pidescoServices;
	private static ProjectBrowserServices browserServices;
	private static JavaEditorServices editorServices;

	@Override
	public void start(BundleContext context) throws Exception {
		final ServiceReference<PidescoServices> ref = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(ref);

		initProjectBrowserServices(context);
		initEditorServices(context);
	}

	private void initProjectBrowserServices(final BundleContext context) {
		final ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(ref);
	}

	private void initEditorServices(final BundleContext context) {
		final ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		editorServices = context.getService(ref);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	public static PidescoServices getPidescoServices() {
		return pidescoServices;
	}

	public static ProjectBrowserServices getBrowserServices() {
		return browserServices;
	}
	
	public static JavaEditorServices getJavaEditorServices() {
		return editorServices;
	}

}
