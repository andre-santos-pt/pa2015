package pt.iscte.pidesco.clazznav;

/**
 * 
 * @author tiagocms
 *
 */
public class Parameters {

	private static Parameters instance;

	public static Parameters getInstance() {
		return instance == null ? new Parameters() : instance;
	}

	private Parameters(){}

	// App configuration
	private boolean DEBUG_MODE = false;


}

