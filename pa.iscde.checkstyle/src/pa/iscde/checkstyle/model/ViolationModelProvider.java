package pa.iscde.checkstyle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the model provided used by views associated to main and
 * detailed report.
 */
public final class ViolationModelProvider {

	/**
	 * Represents the singleton instance of this class.
	 */
	private static ViolationModelProvider INSTANCE;

	/**
	 * The model used by views associated to main and detailed report.
	 */
	private List<Violation> violations;

	/**
	 * Constructor.
	 */
	private ViolationModelProvider() {
		violations = new ArrayList<Violation>();
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return Singleton instance of this class
	 */
	public static synchronized ViolationModelProvider getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ViolationModelProvider();
		}
		return INSTANCE;
	}

	/**
	 * Returns the model used by views associated to main and detailed report.
	 * 
	 * @return model used by views associated to main and detailed report
	 */
	public List<Violation> getViolations() {
		return violations;
	}

	/**
	 * This method resets the model used by views associated to main and
	 * detailed report, when a new check style process is performed.
	 */
	public void resetViolations() {
		violations.clear();
	}

	/**
	 * This method updates the model used by views associated to main and
	 * detailed report, when a new check style process is finished.
	 * 
	 * @param updatedViolations
	 *            The new violations used to update the model.
	 */
	public void updateViolations(Map<String, Violation> updatedViolations) {
		violations.addAll(updatedViolations.values());
	}
}
