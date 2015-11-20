	package pt.iscte.pidesco.clazznav;

import java.io.File;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.internal.JavaEditorServicesImpl;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
//import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;



/**
 * 
 * @author tiagocms
 *
 */
public class Activator implements BundleActivator {

	private static Activator instance;
	
	private static BundleContext context;
	public static JavaEditorServices editor;
	public static JavaEditorListener listener;
//	private static Activator instance;	
	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext bundleContext) throws Exception {
		System.out.println("Starting bundle.");
		
		ServiceReference<JavaEditorServices> reference = bundleContext.getServiceReference(JavaEditorServices.class);
//		System.out.println("Reference:" + reference.toString());
		editor = bundleContext.getService(reference);
		
//		System.out.println("Service:" + editor.toString());
//		System.out.println("File opened " + editor.getOpenedFile());
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
