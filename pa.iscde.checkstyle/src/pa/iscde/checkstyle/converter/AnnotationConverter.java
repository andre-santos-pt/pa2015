package pa.iscde.checkstyle.converter;

import pa.iscde.checkstyle.model.SeverityType;
import pt.iscte.pidesco.javaeditor.service.AnnotationType;

/**
 * TODO
 * 
 */
public final class AnnotationConverter {

	private AnnotationConverter() {
		// Utility class.
	}

	/**
	 * TODO
	 * @param severity
	 * @return
	 */
	public static AnnotationType convert(SeverityType severity) {
		AnnotationType annotationType = null;

		switch (severity) {
		case WARNING:
			annotationType = AnnotationType.WARNING;
			break;
		default:
			annotationType = AnnotationType.WARNING;
		}

		return annotationType;
	}
}
