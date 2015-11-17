package pa.iscde.inspector.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class InspectorAtivator implements BundleActivator {

	private static InspectorAtivator instance;
	@Override
	public void start(BundleContext context) throws Exception {
		instance = this;
		
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
