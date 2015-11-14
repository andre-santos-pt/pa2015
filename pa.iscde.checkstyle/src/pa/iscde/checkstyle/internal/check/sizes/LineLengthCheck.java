package pa.iscde.checkstyle.internal.check.sizes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import pa.iscde.checkstyle.internal.check.Check;
import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;

/**
 * 
 *
 */
public class LineLengthCheck extends Check {

	private static final String CHECK_ID = "LineLengthCheck";

	/**
	 * 
	 */
	private static final String LOG_CHECK_MESSAGE = "Line is longer than '%s' characters.";

	/**
	 * 
	 */
	private static final String LOG_LINE_MESSAGE = "Line is longer than '%s' characters (found '%s').";

	/**
	 * 
	 */
	private static final int MAX_LINE_LENGTH = 80;

	/**
	 * 
	 */
	private static final String IGNORE_PATTERN = "^ *\\* *[^ ]+$";

	/**
	 * 
	 */
	private static final String IMPORT_PATTERN = "^import .*";

	/**
	 * 
	 */
	private String[] lines;

	public LineLengthCheck() {
		super(CHECK_ID, String.format(LOG_CHECK_MESSAGE, MAX_LINE_LENGTH), SeverityType.WARNING);
	}

	@Override
	public void process(Map<String, Violation> violations) {
		lines = getFileLines();
		if (lines == null || lines.length == 0) {
			return;
		}
		processLines(violations);
	}

	/**
	 * TODO
	 * 
	 * @param violations
	 */
	private void processLines(Map<String, Violation> violations) {
		int count = 0;
		int offset = 0;

		final List<ViolationDetail> details = new ArrayList<ViolationDetail>();
		for (int i = 0; i < lines.length; ++i) {
			String line = lines[i];

			int length = line.length();
			offset += length;

			if (length > MAX_LINE_LENGTH && !Pattern.compile(IMPORT_PATTERN).matcher(line).find()
					&& !Pattern.compile(IGNORE_PATTERN).matcher(line).find()) {

				final String detailmessage = String.format(LOG_LINE_MESSAGE, MAX_LINE_LENGTH, length);

				final ViolationDetail violationDetail = new ViolationDetail();
				violationDetail.setResource(resource);
				violationDetail.setLocation(file.getAbsolutePath());
				violationDetail.setLine(i + 1);
				violationDetail.setOffset(offset);
				violationDetail.setMessage(detailmessage);
				violationDetail.setLength(length);

				details.add(violationDetail);

				++count;
			}
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
