package pa.iscde.checkstyle.internal.check.sizes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.io.Files;

import pa.iscde.checkstyle.internal.check.Check;
import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class implements the MethodCountCheck.
 */
public class MethodCountCheck extends Check {

	/**
	 * Identifies this kind of check.
	 */
	private static final String CHECK_ID = "MethodCountCheck";

	/**
	 * The message that should appear in the main report.
	 */
	private static final String LOG_CHECK_MESSAGE = "Class have more than xpto ...";

	/**
	 * The maximum methods count allowed by this check.
	 */

	private static final int MAX_METHOD_COUNT = 10;

	/**
	 * The message that should appear in the detailed report.
	 */
	private static final String LOG_LINE_MESSAGE = "Method count is '%d' (max allowed is '%d').";

	private int nmethods = 0;

	public MethodCountCheck() {
		super(CHECK_ID, String.format(LOG_CHECK_MESSAGE, MAX_METHOD_COUNT), SeverityType.WARNING);
	}

	@Override
	public void process(Map<String, Violation> violations) {

		if (file.isDirectory()) {

			listFileNames = new ArrayList<String>();
			List<String> listFiles = new ArrayList<String>();
			listFiles = getFilesRecursively(file);

			for (String s : listFiles) {

				File file = new File(s);
				setFile(file);
				setResource(file.getName());
				process(violations);

			}

		} else if (file.isFile()) {

			int count = 0;
			nmethods = 0;
			final List<ViolationDetail> details = new ArrayList<ViolationDetail>();
			parseFile(details);

			if (nmethods > MAX_METHOD_COUNT) {
				final String detailmessage = String.format(LOG_LINE_MESSAGE, nmethods, MAX_METHOD_COUNT);
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

	private void parseFile(final List<ViolationDetail> details) {
		String fileContents = null;
		try {
			fileContents = readFile();
		} catch (IOException e) {
			LOGGER.severe(
					String.format("It was possible to read file '%s' due to %s.", file.getName(), e.getMessage()));
		}

		if (fileContents == null) {
			return;
		}

		final ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(fileContents.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		cu.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration node) {
				++nmethods;
				return true;
			}
		});
	}

}
