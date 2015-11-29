package pa.iscde.javadoc.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

import pa.iscde.javadoc.service.JavaDocServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class JavaDocServiceLocator {

	private static JavaDocServiceLocator jdServiceLocatorInstance;

	private static LogService logServiceProxy = (LogService) Proxy.newProxyInstance(
			JavaDocServiceLocator.class.getClassLoader(), new Class[] { LogService.class }, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					StringBuilder sb = new StringBuilder();
					sb.append("LogService Proxy Method:").append(method.getName()).append(" invoked with paramenters:");
					for (Object o : args) {
						sb.append(o).append(";");
					}
					sb.append('\n');
					return null;
				}
			});

	private BundleContext context;

	private HashMap<Class<?>, Object> serviceMap = new HashMap<>();
	
	private JavaDocServiceLocator(final BundleContext ctx) {
		this.context = ctx;
	}

	static void initialize(final BundleContext ctx) {
		jdServiceLocatorInstance = new JavaDocServiceLocator(ctx);
	}

	static void deinitialize() {
		jdServiceLocatorInstance = null;
	}

	// Log is a very special service... if unable to get it from the platform...
	// use your own
	public static LogService getLogService() {
		LogService logService = getService(LogService.class);
		if (null == logService) {
			setService(LogService.class, logServiceProxy);
		}
		return logService;
	}

	public static PidescoServices getPidescoService() {
		return getService(PidescoServices.class);
	}

	public static JavaEditorServices getJavaEditorService() {
		return getService(JavaEditorServices.class);
	}

	public static ProjectBrowserServices getProjectBrowserService() {
		return getService(ProjectBrowserServices.class);
	}

	public static JavaDocServices getJavaDocServices() {
		return getService(JavaDocServices.class);
	}

	public static void setService(Class<?> clazz, Object o) {
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException();
		}
		if (null == jdServiceLocatorInstance) {
			throw new IllegalStateException();
		} else {
			jdServiceLocatorInstance.serviceMap.put(clazz, o);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getService(final Class<T> clazz) {
		if (null == jdServiceLocatorInstance) {
			throw new IllegalStateException();
		} else {
			Object service = jdServiceLocatorInstance.serviceMap.get(clazz);
			if (null == service) {
				service = jdServiceLocatorInstance.getServiceReference(clazz);
				if (null != service) {
					jdServiceLocatorInstance.serviceMap.put(clazz, service);
				}
			}
			return (T) service;
		}
	}

	private <T> T getServiceReference(final Class<T> clazz) {
		T service = null;
		ServiceReference<T> ref = context.getServiceReference(clazz);
		if (null == ref) {
			getLogService().log(LogService.LOG_ERROR, "Unable to get service reference:" + clazz.getName());
		} else {
			service = context.getService(ref);
			if (null == service) {
				getLogService().log(LogService.LOG_ERROR, "Unable to get service:" + clazz.getName());
			}
		}
		return service;
	}

}
