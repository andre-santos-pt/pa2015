package pa.iscde.checkstyle.internal.check.sizes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import pa.iscde.checkstyle.internal.check.Check;
import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;

/**
 * This class implements the LineLengthCheck.
 */
public class LineLengthCheck extends Check {

	/**
	 * Identifies this kind of check.
	 */
	private static final String CHECK_ID = "LineLengthCheck";

	/**
	 * The message that should appear in the main report.
	 */
	private static final String LOG_CHECK_MESSAGE = "Line is longer than '%s' characters.";

	/**
	 * The message that should appear in the detailed report.
	 */
	private static final String LOG_LINE_MESSAGE = "Line is longer than '%s' characters (found '%s').";

	/**
	 * The maximum line length allowed by this check.
	 */
	private static final int MAX_LINE_LENGTH = 80;

	/**
	 * The pattern that should be ignored when this check is being performed.
	 */
	private static final String IGNORE_PATTERN = "^ *\\* *[^ ]+$";

	/**
	 * The import pattern that should be ignored when this check is being
	 * performed.
	 */
	private static final String IMPORT_PATTERN = "^import .*";

	/**
	 * Default construct.
	 */
	public LineLengthCheck() {
		super(CHECK_ID, String.format(LOG_CHECK_MESSAGE, MAX_LINE_LENGTH), SeverityType.WARNING);
	}

	@Override
	public Violation process() {
		this.lines = getFileLines();
		return processLines();
	}

	/**
	 * This method is used to apply this check for each line existing within the
	 * file.
	 * 
	 * @return Return a detected violation or null, if no violation is detected.
	 */
	private Violation processLines() {
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
				violationDetail.setSeverity(severity);
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
			return null;
		}

		final Violation violation = new Violation();
		violation.setSeverity(severity);
		violation.setType(CHECK_ID);
		violation.setDescription(message);
		violation.setCount(count);
		violation.setDetails(details);

		return violation;
	}
}
