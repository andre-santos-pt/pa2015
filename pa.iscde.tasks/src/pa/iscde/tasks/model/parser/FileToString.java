package pa.iscde.tasks.model.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileToString {

	private static final int BUILDER_CAP = 10000;
	private final File f;

	public FileToString(File f) {
		this.f = f;
	}

	public String parse() throws IOException {
		final StringBuilder builder = new StringBuilder(BUILDER_CAP);

		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			while ((line = br.readLine()) != null)
				builder.append(line + "\n");
		}
		
		return builder.toString();
	}
}
