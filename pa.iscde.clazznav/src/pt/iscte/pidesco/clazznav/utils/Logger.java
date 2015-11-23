package pt.iscte.pidesco.clazznav.utils;

import org.osgi.framework.ServiceReference;

public class Logger implements org.eclipse.equinox.log.Logger {

	private static Logger instance = null;

	public Logger() {
		//instance = new Logger();
	}


	@Override
	public void log(int level, String message) {

	}

	@Override
	public void log(int level, String message, Throwable exception) {
		// TODO Auto-generated method stub

	}

	@Override
	public void log(ServiceReference<?> sr, int level, String message) {
		System.out.println("Status: " + message + " - Service: " + sr.getClass().getSimpleName() + " (" + level + ") - " + sr );

	}

	@Override
	public void log(ServiceReference<?> sr, int level, String message, Throwable exception) {
		System.out.println("Service: " + sr + " (" + level + ") - Status: " + message + " - Error: " + exception);
	}

	@Override
	public void log(Object context, int level, String message) {
		System.out.println("Service: " + context + " (" + level + ") - Status: " + message );
	}

	@Override
	public void log(Object context, int level, String message, Throwable exception) {
		System.out.println("Service: " + context + " (" + level + ") - Status: " + message + " - Error: " + exception);
	}

	@Override
	public boolean isLoggable(int level) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Logger getInstance() {
		if(instance == null) {
			instance = new Logger();
		}
		return instance;
	}

}
