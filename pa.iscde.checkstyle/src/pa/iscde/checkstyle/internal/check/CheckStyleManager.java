package pa.iscde.checkstyle.internal.check;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.google.common.io.Files;

import pa.iscde.checkstyle.internal.check.sizes.FileLengthCheck;
import pa.iscde.checkstyle.internal.check.sizes.LineLengthCheck;
import pa.iscde.checkstyle.internal.check.sizes.MethodCountCheck;
import pa.iscde.checkstyle.model.SharedModel;
import pa.iscde.checkstyle.model.Violation;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * This manager is responsible to registered the checks to be performed and
 * process them in order to obtain the potential violations that could be found
 * in the selected classes from project browser component.
 *
 */
public final class CheckStyleManager {

	/**
	 * The file extension on which the check was performed.
	 */
	private static final String JAVA_FILE_EXTENSION = "java";

	/**
	 * Eager instantiation of this Singleton.
	 */
	private static final CheckStyleManager INSTANCE = new CheckStyleManager();

	/**
	 * The list of the registered checks to be performed.
	 */
	private final List<Check> registeredChecks = new ArrayList<Check>();

	/**
	 * The structure to be updated with the violations detected based on the
	 * registered checks. The information in this structure is organized using
	 * as a key the check type (e.g. FileLengthCheck) and the values the
	 * violations detected for the same type.
	 */
	private final Map<String, Violation> violations = new HashMap<String, Violation>();

	/**
	 * This is used to retrieve the registered extension points defined in the
	 * platform.
	 */
	private final IExtensionRegistry extRegistry = Platform.getExtensionRegistry();

	/**
	 * Default constructor.
	 */
	private CheckStyleManager() {
		registeredChecks.add(new LineLengthCheck());
		registeredChecks.add(new FileLengthCheck());
		registeredChecks.add(new MethodCountCheck());
		
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return The singleton instance.
	 */
	public static CheckStyleManager getInstance() {
		return INSTANCE;
	}

	/**
	 * This method is used to start the check style process using for that end
	 * the registered checks.
	 */
	public void process() {
		addCheckExtensionPoints();
		resetViolations();
		recalculateViolations();
	}

	/**
	 * This method is used to return the detected violations after the check
	 * style process is finished.
	 * 
	 * @return The detected violations.
	 */
	public Map<String, Violation> getViolations() {
		return Collections.unmodifiableMap(violations);
	}

	/**
	 * This method is used recalculate the violations for the selected source
	 * elements, using for that end the registered checks. It's important to
	 * notice that the selected source elements are provided by the shared
	 * model.
	 */
	private void recalculateViolations() {
		final Collection<SourceElement> elements = SharedModel.getInstance().getElements();
		for (SourceElement element : elements) {
			final File file = element.getFile();
			final List<File> childFiles = new ArrayList<File>();

			if (!element.isClass() && file.isDirectory()) {
				searchFilesRecursively(file, childFiles);
			} else if (element.isClass()) {
				childFiles.add(file);
			}

			for (Check check : registeredChecks) {
				for (File childFile : childFiles) {
					check.setResource(childFile.getName());
					check.setFile(childFile);

					final Violation calculatedViolation = check.process();
					final Violation existingViolation = violations.get(check.getCheckId());

					if (calculatedViolation == null) {
						continue;
					}

					if (existingViolation == null) {
						violations.put(check.getCheckId(), calculatedViolation);
					} else {
						existingViolation.getDetails().addAll(calculatedViolation.getDetails());
						existingViolation.setCount(existingViolation.getCount() + calculatedViolation.getCount());
					}
				}
			}
		}
	}

	/**
	 * This method is used to reset the structure used to populate while the
	 * check style process is being processed.
	 */
	private void resetViolations() {
		violations.clear();
	}

	/**
	 * This method is used to load and execute the check extension points
	 * implemented by other components.
	 */
	private void addCheckExtensionPoints() {
		final IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.checkstyle.check");

		final IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension extension : extensions) {
			final IConfigurationElement comp = extension.getConfigurationElements()[0];
			try {
				final Check check = (Check) comp.createExecutableExtension("class");
				this.registeredChecks.add(check);
			} catch (CoreException e) {
				System.out.println(String.format(
						"It was not possible to execute the method addChecks for the extension with id %s",
						extension.getUniqueIdentifier()));
			}
		}
	}

	/**
	 * This method is used to search recursively all the child files that are
	 * within a certain folder.
	 * 
	 * @param childFiles
	 *            The list of child files existing within a certain directory.
	 * @param root
	 *            The root (directory) from which the search of the child files
	 *            should be performed.
	 */
	public void searchFilesRecursively(final File root, List<File> childFiles) {
		final File[] list = root.listFiles();

		if (list == null) {
			return;
		}

		for (File f : list) {
			if (f.isDirectory()) {
				searchFilesRecursively(f, childFiles);
			} else {
				String fileExtension = (String) Files.getFileExtension(f.getAbsolutePath());
				if (fileExtension.equals(JAVA_FILE_EXTENSION)) {
					childFiles.add(f);
				}
			}
		}
	}
}
