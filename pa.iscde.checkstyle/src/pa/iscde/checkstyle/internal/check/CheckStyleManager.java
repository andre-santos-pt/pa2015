package pa.iscde.checkstyle.internal.check;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pa.iscde.checkstyle.internal.check.sizes.FileLengthCheck;
import pa.iscde.checkstyle.internal.check.sizes.LineLengthCheck;
import pa.iscde.checkstyle.model.SharedModel;
import pa.iscde.checkstyle.model.Violation;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * TODO
 *
 */
public final class CheckStyleManager {

	private static final int NUMBER_REGISTERED_CHECKS = 1;

	/**
	 * Eager instantiation of this Singletion.
	 */
	private static final CheckStyleManager INSTANCE = new CheckStyleManager();

	/**
	 * 
	 */
	private final List<Check> registeredChecks = new ArrayList<Check>(NUMBER_REGISTERED_CHECKS);

	/**
	 * 
	 */
	private final Map<String, Violation> violations = new HashMap<String, Violation>();

	private CheckStyleManager() {
		registeredChecks.add(new LineLengthCheck());
		registeredChecks.add(new FileLengthCheck());
	}

	public static CheckStyleManager getInstance() {
		return INSTANCE;
	}

	/**
	 * TODO
	 */
	public void process() {
		resetViolations();
		recalculateViolations();

	}

	private void recalculateViolations() {
		final Collection<SourceElement> elements = SharedModel.getInstance().getElements();
		for (SourceElement element : elements) {
			for (Check check : registeredChecks) {
				check.setResource(element.getName());
				check.setFile(element.getFile());
				check.process(violations);
			}
		}
	}

	private void resetViolations() {
		violations.clear();
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Violation> getViolations() {
		return Collections.unmodifiableMap(violations);
	}
}
