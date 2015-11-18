package pa.iscde.checkstyle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ViolationModelProvider {
	private static ViolationModelProvider INSTANCE;

	private List<Violation> violations;

	private ViolationModelProvider() {
		violations = new ArrayList<Violation>();
	}

	public static synchronized ViolationModelProvider getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ViolationModelProvider();
		}
		return INSTANCE;
	}

	public List<Violation> getViolations() {
		return violations;
	}

	public void resetViolations() {
		violations.clear();
	}

	public void updateViolations(Map<String, Violation> updatedViolations) {
		violations.addAll(updatedViolations.values());
	}
}
