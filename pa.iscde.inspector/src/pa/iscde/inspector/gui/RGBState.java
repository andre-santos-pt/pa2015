package pa.iscde.inspector.gui;

import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.BundleEvent;

public class RGBState {

	static RGB STARTED() {
		return new RGB(100, 255, 0);
	}

	static RGB INSTALLED() {
		return new RGB(0, 100, 0);
	}

	static RGB STOPED() {
		return new RGB(255, 0, 0);
	}

	public static RGB GET_COLOR(int type) {
		switch (type) {
		case BundleEvent.STARTED:
			System.out.println("started");
			return STARTED();
		case BundleEvent.STOPPED:
			System.out.println("STOPPED");
			return STOPED();
		case BundleEvent.INSTALLED:
			System.out.println("INSTALLED");
			return INSTALLED();
		case BundleEvent.LAZY_ACTIVATION:
			System.out.println("LAZY_ACTIVATION");
			return LAZY_ACTIVATION();
		case BundleEvent.RESOLVED:
			System.out.println("RESOLVED");
			return RESOLVED();
		case BundleEvent.STARTING:
			System.out.println("STARTING");
			return STARTING();
		case BundleEvent.STOPPING:
			System.out.println("STOPPING");
			return STOPPING();
		case BundleEvent.UNINSTALLED:
			System.out.println("UNINSTALLED");
			return UNINSTALLED();
		case BundleEvent.UPDATED:
			System.out.println("UPDATED");
			return UPDATED();
		default:
			return UNINSTALLED();
		}

	}

	static RGB UPDATED() {
		return new RGB(255, 50, 100);

	}

	static RGB UNINSTALLED() {
		return new RGB(255, 255, 255);

	}

	static RGB STARTING() {
		return new RGB(0, 100, 100);

	}

	static RGB STOPPING() {
		return new RGB(100, 0, 0);
	}

	static RGB RESOLVED() {
		return new RGB(0, 255, 0);
	}

	static RGB LAZY_ACTIVATION() {
		return new RGB(255, 255, 0);
	}

}
