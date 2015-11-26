package pa.iscde.checkstyle.internal.check.sizes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.File;

import pa.iscde.checkstyle.internal.check.Check;
import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;

/**
 * This class implements the FileLengthCheck.
 */
public class FileLengthCheck extends Check {

	/**
	 * Identifies this kind of check.
	 */
	private static final String CHECK_ID = "FileLengthCheck";

	/**
	 * Default maximum number of lines.
	 */
	private static final int MAX_LINES = 10;

	/**
	 * The message that should appear in the main report.
	 */
	private static final String LOG_CHECK_MESSAGE = "File length is grater than max allowed (%d).";

	/**
	 * The message that should appear in the detailed report.
	 */
	private static final String LOG_LINE_MESSAGE = "File length is '%d' lines (max allowed is '%d').";

	protected List<String> listFiles;
	
	/**
	 * Default construct.
	 */
	public FileLengthCheck() {
		super(CHECK_ID, String.format(LOG_CHECK_MESSAGE, MAX_LINES), SeverityType.WARNING);
	}

	@Override
	public void process(Map<String, Violation> violations) {
		
		
if (file.isDirectory()){
			
			listFileNames = new ArrayList<String>();
			List<String> listFiles = new ArrayList<String>();
			listFiles = getFilesrecursively(file);
			
			for ( String s : listFiles ) {
				File file = new File( s );
				setFile(file);
				process(violations);
			}
		
		} else if (file.isFile()){
		
			lines = getFileLines();
	
			if (lines == null || lines.length == 0) {
				return;
			}
	
			int count = 0;
			final List<ViolationDetail> details = new ArrayList<ViolationDetail>();
			if (lines.length > MAX_LINES) {
				final String detailmessage = String.format(LOG_LINE_MESSAGE, lines.length, MAX_LINES);
	
				final ViolationDetail violationDetail = new ViolationDetail();
				violationDetail.setSeverity(severity);
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
}
