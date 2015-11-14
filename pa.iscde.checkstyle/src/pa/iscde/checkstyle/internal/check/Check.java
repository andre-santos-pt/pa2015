package pa.iscde.checkstyle.internal.check;

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

import com.google.common.base.Strings;
import com.google.common.io.Closeables;

import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;

/**
 * 
 *
 */
public abstract class Check {
	private static final Logger LOGGER = Logger.getLogger(Check.class.getName());

	/**
	 * The number of characters to read at once.
	 */
	private static final int READ_BUFFER_SIZE = 1024;
	
	protected String checkId;

	protected String message;

	protected String resource;

	protected SeverityType severity;

	protected File file;

	protected Check(String checkId, String message, SeverityType severity) {
		this.checkId = checkId;
		this.message = message;
		this.severity = severity;
	}

	public String getCheckId() {
		return checkId;
	}

	public SeverityType getSeverity() {
		return severity;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public abstract void process(Map<String, Violation> violations);

	/**
	 * TODO
	 * 
	 * @param fileContents
	 * @return
	 */
	public String[] getFileLines() {
		final List<String> textLines = new ArrayList<>();
		try {
			String fileContents = readFile();

			if (Strings.isNullOrEmpty(fileContents)) {
				return null;
			}

			final BufferedReader reader = new BufferedReader(new StringReader(fileContents));
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
		return textLines.toArray(new String[textLines.size()]);
	}

	/**
	 * TODO
	 * 
	 * @return
	 * @throws IOException
	 */
	private String readFile() throws IOException {
		if (file == null) {
			return null;
		}

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
