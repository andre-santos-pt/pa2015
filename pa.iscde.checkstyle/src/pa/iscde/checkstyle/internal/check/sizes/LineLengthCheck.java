package pa.iscde.checkstyle.internal.check.sizes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.common.io.Closeables;

import pa.iscde.checkstyle.internal.check.Check;
import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;

/**
 * 
 *
 */
public class LineLengthCheck extends Check {

	private static final Logger LOGGER = Logger.getLogger(LineLengthCheck.class.getName());

	private static final String CHECK_ID = "LineLengthCheck";

	/**
	 * The number of characters to read at once.
	 */
	private static final int READ_BUFFER_SIZE = 1024;

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
	private File file;

	/**
	 * 
	 */
	private String[] lines;

	public LineLengthCheck(String resource, File file) {
		super(CHECK_ID, String.format(LOG_CHECK_MESSAGE, MAX_LINE_LENGTH), resource, SeverityType.WARNING);
		this.file = file;
	}

	@Override
	public void process(Map<String, Violation> violations) {
		String fileContents = null;
		try {
			fileContents = readFile();
		} catch (IOException e) {
			LOGGER.severe(
					String.format("It was possible to read file '%s' due to %s.", file.getName(), e.getMessage()));
		}

		if (fileContents != null) {
			final List<String> textLines = getFileLines(fileContents);
			lines = textLines.toArray(new String[textLines.size()]);
			processLines(violations);
		}
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

		if(count == 0){
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

	/**
	 * TODO
	 * 
	 * @param fileContents
	 * @return
	 */
	private List<String> getFileLines(String fileContents) {
		final List<String> textLines = new ArrayList<>();
		final BufferedReader reader = new BufferedReader(new StringReader(fileContents));

		try {
			while (true) {
				final String line = reader.readLine();
				if (line == null) {
					break;
				}
				textLines.add(line);
			}
		} catch (IOException e) {
			LOGGER.severe(
					String.format("It was possible to read file '%s' due to %s.", file.getName(), e.getMessage()));
		}
		return textLines;
	}

	/**
	 * TODO
	 * 
	 * @return
	 * @throws IOException
	 */
	private String readFile() throws IOException {
		final StringBuilder buf = new StringBuilder();
		final FileInputStream stream = new FileInputStream(file);
		final Reader reader = new InputStreamReader(stream);

		try {
			final char[] chars = new char[READ_BUFFER_SIZE];
			while (true) {
				final int len = reader.read(chars);
				if (len < 0) {
					break;
				}
				buf.append(chars, 0, len);
			}
		} finally {
			Closeables.close(reader, false);
		}
		return buf.toString();
	}
}
