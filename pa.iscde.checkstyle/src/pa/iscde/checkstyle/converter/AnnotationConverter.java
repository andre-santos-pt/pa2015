package pa.iscde.checkstyle.converter;

import pa.iscde.checkstyle.model.SeverityType;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;

/**
 * This utility class is used to perform annotation conversions between the
 * CheckStyle model and JavaEditor model.
 * 
 */
public final class AnnotationConverter {

	private AnnotationConverter() {
		// Utility class.
	}

	/**
	 * This method converts a severity type into a annotation type.
	 * 
	 * @param severity
	 *            The severity type to be converted,
	 * @return An annotation type.
	 */
	public static AnnotationType convert(SeverityType severity) {
		AnnotationType annotationType = null;

		switch (severity) {
		case WARNING:
			annotationType = AnnotationType.WARNING;
			break;
		case BLOCKED:
		case CRITICAL:
			annotationType = AnnotationType.ERROR;
			break;
		default:
			annotationType = AnnotationType.WARNING;
		}

		return annotationType;
	}
}
