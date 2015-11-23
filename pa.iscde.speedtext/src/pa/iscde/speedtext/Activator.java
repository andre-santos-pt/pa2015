package pa.iscde.speedtext;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;


public class Activator implements BundleActivator  {

	private static Activator speedTestActivator;
	private BundleContext context;
	private JavaEditorServices javaEditorservice;
	private ProjectBrowserServices projectBrowserServices;
	private SpeedTextService speedTestservice;
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		speedTestActivator = this;
		this.context = context;
		ServiceReference<JavaEditorServices> editor = context.getServiceReference(JavaEditorServices.class);
		javaEditorservice = context.getService(editor);
		ServiceReference<ProjectBrowserServices> browser = context.getServiceReference(ProjectBrowserServices.class);
		projectBrowserServices = context.getService(browser);
		
//		context.registerService(PidescoView.class, speedTestservice, null);
	}

	public ProjectBrowserServices getProjectBrowserServices() {
		return projectBrowserServices;
	}

	public JavaEditorServices getJavaEditorservice() {
		return javaEditorservice;
	}

	public BundleContext getContext() {
		return context;
	}

	public SpeedTextService getSpeedTestservice() {
		return speedTestservice;
	}

	public static Activator getActivator( ){
		return speedTestActivator;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
	}
	


}
