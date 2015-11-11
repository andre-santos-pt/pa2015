package pa.iscde.checkstyle.model;

import java.util.ArrayList;
import java.util.List;

public final class ViolationModelProvider {
	private static ViolationModelProvider INSTANCE;

	private List<Violation> violations;

	private ViolationModelProvider() {
		violations = new ArrayList<Violation>();
		violations.add(new Violation(SeverityType.WARNING, "Violation Type 1", 1, "Description 1", null));
		violations.add(new Violation(SeverityType.CRITICAL, "Violation Type 2", 2, "Description 2", null));
		violations.add(new Violation(SeverityType.BLOCKED, "Violation Type 3", 3, "Description 3", null));
		violations.add(new Violation(SeverityType.CRITICAL, "Violation Type 4", 4, "Description 4", null));
		violations.add(new Violation(SeverityType.WARNING, "Violation Type 5", 5, "Description 5", null));
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
}
