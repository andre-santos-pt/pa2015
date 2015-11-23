package pt.iscte.pidesco.clazznav;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.clazznav.core.OpenFileListener;
import pt.iscte.pidesco.clazznav.utils.Logger;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
//import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
/**
 * 
 * @author tiagocms
 *
 */
public class Activator implements BundleActivator {

//	private static Activator instance;

	private Logger log = new Logger();

	private static BundleContext context;

	public static ServiceReference<JavaEditorServices> javaEditorReference;
	public static JavaEditorServices javaEditorService;

	public static ServiceReference<ProjectBrowserServices> projectBrowserReference;
	public static ProjectBrowserServices projectBrowserService;

	public static OpenFileListener listener;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext bundleContext) throws Exception {
		log.log(0, "Starting bundle" );

		//Chamada a serviço JavaEditor
		javaEditorReference = bundleContext.getServiceReference( JavaEditorServices.class );
		log.log(javaEditorReference, 0, "Starting...");
		javaEditorService = bundleContext.getService( javaEditorReference );

		//Chamada a serviço ProjectBrowser
		projectBrowserReference = bundleContext.getServiceReference( ProjectBrowserServices.class );
		log.log(projectBrowserReference, 0, "Starting...");
		projectBrowserService = bundleContext.getService( projectBrowserReference );

		//
		listener = new OpenFileListener();
		javaEditorService.addListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		bundleContext.ungetService(javaEditorReference);
		bundleContext.ungetService(javaEditorReference);
		
	}

	static BundleContext getContext() {
		return context;
	}


}
