package pa.iscde.checkstyle.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.checkstyle.internal.listener.SelectedClassListener;
import pa.iscde.checkstyle.internal.listener.SelectedItemListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

/**
 * This class is responsible to register the service associated to CheckStyle
 * component and to initialize the external services and listeners needed by it.
 *
 */
public class CheckStyleActivator implements BundleActivator {

	/**
	 * The Singleton instance of this class.
	 */
	private static CheckStyleActivator INSTANCE;

	/**
	 * Context from which the registered external services are obtained.
	 */
	private BundleContext context;

	/**
	 * A project browser listener that is added to project browser component in
	 * order to receive the notifications from events detected on it.
	 */
	private ProjectBrowserListener pblistener;

	/**
	 * Holds a reference for services exposed by project browser component.
	 */
	private ProjectBrowserServices pbServices;

	/**
	 * A project browser listener that is added to project browser component in
	 * order to receive the notifications from events detected on it.
	 */
	private JavaEditorListener jeListener;

	/**
	 * Holds a reference for services exposed by Java editor component.
	 */
	private JavaEditorServices jeServices;

	/**
	 * Return the single INSTANCE of this class.
	 * 
	 * @return INSTANCE
	 */
	public static CheckStyleActivator getInstance() {
		return INSTANCE;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		INSTANCE = this;
		this.context = context;

		setupExternalServices();
		addListenersToExternalServices();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		this.context = null;

		removeListenersFromExternalServices();
		teardownExternalServices();
	}

	/**
	 * This method is used to return the reference to Java Editor component
	 * services.
	 * 
	 * @return The reference to Java Editor component services.
	 */
	public JavaEditorServices getJavaEditorServices() {
		return this.jeServices;
	}
	
	/**
	 * This method is used to obtain the references for services exposed by
	 * project browser component and Java editor component.
	 */
	private void setupExternalServices() {
		final ServiceReference<ProjectBrowserServices> refPbServices = context
				.getServiceReference(ProjectBrowserServices.class);
		pbServices = context.getService(refPbServices);

		final ServiceReference<JavaEditorServices> refJeServices = context
				.getServiceReference(JavaEditorServices.class);
		jeServices = context.getService(refJeServices);
	}

	/**
	 * This method is used to clean the references for services exposed by
	 * project browser component and Java editor component.
	 */
	private void teardownExternalServices() {
		pbServices = null;
		jeServices = null;
	}

	/**
	 * This method is used to initialize and to add the listeners for project
	 * browser and Java editor components in order to receive the notifications
	 * from events detected on them.
	 */
	private void addListenersToExternalServices() {
		pblistener = new SelectedItemListener();
		pbServices.addListener(pblistener);

		jeListener = new SelectedClassListener(jeServices);
		jeServices.addListener(jeListener);
	}

	/**
	 * This method is used to remove the listeners for project browser and Java
	 * editor components.
	 */
	private void removeListenersFromExternalServices() {
		pbServices.removeListener(pblistener);
		pblistener = null;

		jeServices.removeListener(jeListener);
		jeListener = null;
	}
}
