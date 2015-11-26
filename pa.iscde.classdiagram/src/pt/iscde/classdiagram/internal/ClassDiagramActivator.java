package pt.iscde.classdiagram.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ClassDiagramActivator extends ProjectBrowserListener.Adapter implements BundleActivator {

	private static ClassDiagramActivator instance;
	private PidescoServices pidescoServices;
	private ProjectBrowserServices browserServices;
	private JavaEditorServices javaEditorServices;

	public static ClassDiagramActivator getInstance() {
		return instance;
	}

	public PidescoServices getPidescoServices() {
		return pidescoServices;
	}

	public ProjectBrowserServices getBrowserServices() {
		return browserServices;
	}

	public JavaEditorServices getJavaEditorServices() {
		return javaEditorServices;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		instance = this;
		ServiceReference<PidescoServices> ref = context.getServiceReference(PidescoServices.class);
		pidescoServices = context.getService(ref);

		ServiceReference<ProjectBrowserServices> refProjectBrowserServices = context
				.getServiceReference(ProjectBrowserServices.class);
		browserServices = context.getService(refProjectBrowserServices);

		ServiceReference<JavaEditorServices> refJavaEditorServices = context
				.getServiceReference(JavaEditorServices.class);
		javaEditorServices = context.getService(refJavaEditorServices);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		instance = null;
	}

}
