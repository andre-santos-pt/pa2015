package pa.iscde.javadoc.internal;

import org.osgi.service.log.LogService;

import pa.iscde.javadoc.service.JavaDocServices;

public class JavaDocServicesImplementation implements JavaDocServices {

	private final LogService logService;

	public JavaDocServicesImplementation(final LogService logService) {
		this.logService = logService;
	}

}
