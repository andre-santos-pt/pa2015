package pa.iscde.checkstyle.internal;

import pa.iscde.checkstyle.internal.check.CheckStyleManager;
import pa.iscde.checkstyle.model.ViolationModelProvider;
import pa.iscde.checkstyle.service.CheckStyleService;

public class CheckStyleServiceImpl implements CheckStyleService {

	public void process() {
		ViolationModelProvider.getInstance().resetViolations();
		CheckStyleManager.getInstance().process();
		ViolationModelProvider.getInstance().updateViolations(CheckStyleManager.getInstance().getViolations());
	}
}
