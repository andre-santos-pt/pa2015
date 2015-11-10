package pa.iscde.checkstyle.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.checkstyle.internal.listener.SelectItemListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class CheckStyleActivator implements BundleActivator{

	private static CheckStyleActivator instance;

	private BundleContext context;
	
	private ProjectBrowserListener pblistener;

	private ProjectBrowserServices pbServices;
	
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		this.context = context;
		
		final ServiceReference<ProjectBrowserServices> ref = context.getServiceReference(ProjectBrowserServices.class);
		pbServices = context.getService(ref);
		
		pblistener = new SelectItemListener();
		pbServices.addListener(pblistener);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
		this.context = null;
	}
}
