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
 * This manager is responsible to registered the checks to be performed and
 * process them in order to obtain the potential violations that could be found
 * in the selected classes from project browser component.
 *
 */
public final class CheckStyleManager {

	/**
	 * Indicates the number of registered checks.
	 */
	private static final int NUMBER_REGISTERED_CHECKS = 2;

	/**
	 * Eager instantiation of this Singleton.
	 */
	private static final CheckStyleManager INSTANCE = new CheckStyleManager();

	/**
	 * The list of the registered checks to be performed.
	 */
	private final List<Check> registeredChecks = new ArrayList<Check>(NUMBER_REGISTERED_CHECKS);

	/**
	 * The structure to be updated with the violations detected based on the
	 * registered checks. The information in this structure is organized using
	 * as a key the check type (e.g. FileLengthCheck) and the values the
	 * violations detected for the same type.
	 */
	private final Map<String, Violation> violations = new HashMap<String, Violation>();

	/**
	 * Default constructor.
	 */
	private CheckStyleManager() {
		registeredChecks.add(new LineLengthCheck());
		registeredChecks.add(new FileLengthCheck());
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return The singleton instance.
	 */
	public static CheckStyleManager getInstance() {
		return INSTANCE;
	}

	/**
	 * This method is used to start the check style process using for that end
	 * the registered checks.
	 */
	public void process() {
		resetViolations();
		recalculateViolations();
	}

	/**
	 * This method is used recalculate the violations for the selected source
	 * elements, using for that end the registered checks. It's important to
	 * notice that the selected source elements are provided by a shared model.
	 */
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

	/**
	 * This method is used to reset the structure used to populate while the
	 * check style process is being processed.
	 */
	private void resetViolations() {
		violations.clear();
	}

	/**
	 * This method is used to return the detected violations after the check
	 * style process is finished.
	 * 
	 * @return The detected violations.
	 */
	public Map<String, Violation> getViolations() {
		return Collections.unmodifiableMap(violations);
	}
}
