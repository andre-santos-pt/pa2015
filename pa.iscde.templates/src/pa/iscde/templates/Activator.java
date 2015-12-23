package pa.iscde.templates;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

//import pa.iscde.templates.listener.TemplatesListener;
//import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */

public class Activator implements BundleActivator {

	private static Activator templatesActivator;
	private BundleContext context;
	private JavaEditorServices javaEditorservice;
	private ProjectBrowserServices projectBrowserServices;
	//private TemplateViewer templatesService;
	
	//private JavaEditorListener javaEditorListener;
	
	private void LoadExternalServices() {
		final ServiceReference<ProjectBrowserServices> refPbServices = context
				.getServiceReference(ProjectBrowserServices.class);
		projectBrowserServices = context.getService(refPbServices);

		final ServiceReference<JavaEditorServices> refJeServices = context
				.getServiceReference(JavaEditorServices.class);
		javaEditorservice = context.getService(refJeServices);
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		templatesActivator = this;
		this.context = context;
		LoadExternalServices();
		addListeners();
	}
	private void addListeners() {
//		javaEditorListener = new TemplatesListener(javaEditorservice);
//		javaEditorservice.addListener(javaEditorListener);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		templatesActivator = null;
		this.context = null;
	}

	public ProjectBrowserServices getProjectBrowserServices() {
		return projectBrowserServices;
	}

	public JavaEditorServices getJavaEditorservice() {
		return javaEditorservice;
	}
	
	public static Activator getActivator( ){
		return templatesActivator;
	}
	
	public BundleContext getContext() {
		return context;
	}
}
