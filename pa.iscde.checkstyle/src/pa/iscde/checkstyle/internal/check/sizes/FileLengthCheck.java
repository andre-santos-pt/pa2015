package pa.iscde.checkstyle.internal.check.sizes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pa.iscde.checkstyle.internal.check.Check;
import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;

public class FileLengthCheck extends Check {
	/**
	 * Default maximum number of lines.
	 */
	private static final int MAX_LINES = 10;

	/**
	 * 
	 */
	private static final String CHECK_ID = "FileLengthCheck";

	/**
	 * 
	 */
	private static final String LOG_CHECK_MESSAGE = "File length is grater than max allowed (%d).";

	/**
	 * 
	 */
	private static final String LOG_LINE_MESSAGE = "File length is '%d' lines (max allowed is '%d').";

	/**
	 * 
	 */
	private String[] lines;

	public FileLengthCheck() {
		super(CHECK_ID, String.format(LOG_CHECK_MESSAGE, MAX_LINES), SeverityType.WARNING);
	}

	@Override
	public void process(Map<String, Violation> violations) {
		lines = getFileLines();

		if (lines == null || lines.length == 0) {
			return;
		}

		int count = 0;
		final List<ViolationDetail> details = new ArrayList<ViolationDetail>();
		if (lines.length > MAX_LINES) {
			final String detailmessage = String.format(LOG_LINE_MESSAGE, lines.length, MAX_LINES);

			final ViolationDetail violationDetail = new ViolationDetail();
			violationDetail.setResource(resource);
			violationDetail.setLocation(file.getAbsolutePath());
			violationDetail.setMessage(detailmessage);

			details.add(violationDetail);

			++count;
		}

		if (count == 0) {
			return;
		}

		Violation violation = violations.get(CHECK_ID);

		if (violation == null) {
			violation = new Violation();
			violation.setSeverity(severity);
			violation.setType(CHECK_ID);
			violation.setDescription(message);
			violation.setCount(count);
			violation.setDetails(details);
			violations.put(CHECK_ID, violation);
		} else {
			violation.getDetails().addAll(details);
			violation.setCount(violation.getCount() + count);
		}
	}
}
