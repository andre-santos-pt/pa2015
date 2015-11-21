	package pt.iscte.pidesco.clazznav;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.clazznav.core.OpenFileListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
//import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;
import pt.iscte.pidesco.clazznav.utils.Logger;
/**
 * 
 * @author tiagocms
 *
 */
public class Activator implements BundleActivator {

	private static Activator instance;
	
	private Logger log = new Logger();
	
	private static BundleContext context;
	public static JavaEditorServices editor;
	public static ProjectBrowserServices browser;
	public static OpenFileListener listener;
	
//	private static Activator instance;	
	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext bundleContext) throws Exception {
		log.log(0, "Starting bundle");
		
		ServiceReference<JavaEditorServices> reference = bundleContext.getServiceReference(JavaEditorServices.class);
		log.log(reference, 0, "Starting...");
		editor = bundleContext.getService(reference);
		
				
		ServiceReference<ProjectBrowserServices> reference2 = bundleContext.getServiceReference(ProjectBrowserServices.class);
		log.log(reference2, 0, "Starting...");
		browser = bundleContext.getService(reference2);
		
		listener = new OpenFileListener(editor);
		
		
		editor.addListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}
	
	public static Activator getInstance() {
		return instance;
	}

}
