package pa.iscde.checkstyle.extensibility;

/**
 * This interface defines the methods that could be implemented by other
 * components in order to change the default configurations that are associated
 * to the registered checks existing in the CheckStyle-4-ISCDE.
 */
public interface ICheckConfigExtensionPoint {

	/**
	 * This method will be used to define a new string value for a property
	 * associated to a certain check.
	 * 
	 * @param checkId
	 *            The identification of the check type to be modified.
	 * @param propertyId
	 *            The identification of the property which value should be
	 *            changed.
	 * @return The new value or null, if the extension does not want to change
	 *         the default value defined for the property.
	 */
	public String getStringPropertyValue(String checkId, String propertyId);

	/**
	 * This method will be used to define a new integer value for a property
	 * associated to a certain check.
	 * 
	 * @param checkId
	 *            he identification of the check type to be modified.
	 * @param propertyId
	 *            The identification of the property which value should be
	 *            changed.
	 * @return The new value or null, if the extension does not want to change
	 *         the default value defined for the property.
	 */
	public Integer getIntegerPropertyValue(String checkId, String propertyId);
}
