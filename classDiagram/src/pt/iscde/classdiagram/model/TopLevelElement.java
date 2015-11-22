package pt.iscde.classdiagram.model;

import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.IFigure;

import pt.iscde.classdiagram.model.types.EModifierType;
import pt.iscde.classdiagram.model.types.ETopElementType;

/**
 * This interface should be implemented to contain the class diagram's top
 * elements (e.g. classes, interfaces,...)
 * 
 * @author joaocarias
 *
 */
public interface TopLevelElement {
	/**
	 * Gets the element unique ID
	 * 
	 * @return a {@link String} with this element ID
	 */
	public String getId();

	/**
	 * Gets this element name. For example, the Class name.
	 * @return a {@link String} with this element Name
	 */
	public String getName();

	
	/**
	 * Gets this element Type (Class, Abstract Class, Interface or Enum)
	 * @return this element's {@link ETopElementType} 
	 */
	public ETopElementType getClassType();

	
	/**
	 * Gets this element Type (Class, Abstract Class, Interface or Enum)
	 * @return
	 */
	public List<TopLevelElement> getConnectedTo();
	
	public void addAttribute(ChildElementTemplate childElement);
	
	public void addMethod(ChildElementTemplate childElement);
	
	public IFigure getFigure();
	
	public void addMmodifier(EModifierType modifierType);
	
}
