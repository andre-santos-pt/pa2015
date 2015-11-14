package pa.iscde.checkstyle.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pa.iscde.checkstyle.internal.check.sizes.LineLengthCheck;
import pa.iscde.checkstyle.model.SharedModel;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationModelProvider;
import pa.iscde.checkstyle.service.CheckStyleService;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class CheckStyleServiceImpl implements CheckStyleService {

	public void process() {
		ViolationModelProvider.getInstance().resetViolations();

		final Collection<SourceElement> elements = SharedModel.getInstance().getElements();

		final Map<String, Violation> violations = new HashMap<String, Violation>();

		for (SourceElement element : elements) {
			final LineLengthCheck check = new LineLengthCheck(element.getName(), element.getFile());
			check.process(violations);
		}

		ViolationModelProvider.getInstance().updateViolations(violations);
	}
}
