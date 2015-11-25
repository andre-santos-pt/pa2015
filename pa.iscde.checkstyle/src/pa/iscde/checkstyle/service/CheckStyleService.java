package pa.iscde.checkstyle.service;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;

/**
 * Service containing the operations offered by the CheckStyle component.
 */
public interface CheckStyleService {

	/**
	 * This method returns all checks' types that are available in the
	 * CheckStyle component.
	 * 
	 * @return A list of checks' types.
	 */
	List<String> getChecks();

	/**
	 * This method enables a check in the CheckStyle component.
	 * 
	 * @param type
	 *            The check type (e.g. FileLengthCheck) to be enabled.
	 */
	void enableCheck(String type);

	/**
	 * This method disables a check in the CheckStyle component.
	 * 
	 * @param type
	 *            The check type (e.g. FileLengthCheck) to be disabled.
	 */
	void disableCheck(String type);

	/**
	 * This method is used to determine the violations associated to a specific
	 * source file or from a specific package.
	 * 
	 * @param file
	 *            The source file from which the violations should be
	 *            determined.
	 * @return The list of violations or an empty list if no violations have
	 *         been detected.
	 */
	List<Violation> getViolations(File file);

	/**
	 * This method is used to determine the violations associated to a specific
	 * method of a certain source file.
	 * 
	 * @param file
	 *            The source file.
	 * @param method
	 *            The method of the source file for which the violations should
	 *            be determined.
	 * @return The list of violations or an empty list if no violations have
	 *         been detected.
	 */
	List<Violation> getViolations(File file, Method method);
	
	/**
	 * This method changes the default severity for check in the CheckStyle component.
	 * 
	 * @param type
	 *            The check type (e.g. FileLengthCheck) to be changed.
	 * @param severity
	 *            The new severity type for the check type.	 
	 */
	void changeSeverity(String type, SeverityType severity);
}
