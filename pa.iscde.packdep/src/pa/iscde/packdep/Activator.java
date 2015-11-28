package pa.iscde.packdep;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;



public class Activator implements BundleActivator {

	private static JavaEditorServices editor_service;
	private static ProjectBrowserServices project_service;

	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference<JavaEditorServices> editor_service_reference = context.getServiceReference(JavaEditorServices.class);
		editor_service = context.getService(editor_service_reference);
		ServiceReference<ProjectBrowserServices> project_service_reference = context.getServiceReference(ProjectBrowserServices.class);
		project_service = context.getService(project_service_reference);
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		
		
	}
	
	public static JavaEditorServices getEditorService(){
		return editor_service;
	}
	
	public static ProjectBrowserServices getProjectService(){
		return project_service;
	}

}
