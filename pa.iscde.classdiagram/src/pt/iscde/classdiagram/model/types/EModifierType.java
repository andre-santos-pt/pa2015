package pt.iscde.classdiagram.model.types;

import java.util.Map;

import org.eclipse.swt.graphics.Image;

public enum EModifierType {
	PACKAGE,
	PRIVATE,
	PUBLIC,
	PROTECTED,
	STATIC,
	FINAL,
	ABSTRACT,
	SYNCHRONIZED,
	VOLATILE,
	TRANSIENT, 
	CONSTRUCTOR;
	
	public static Image getModifierIcon(EModifierType modifierType, Map<String, Image> imageMap) {
		switch (modifierType) {
		case ABSTRACT:
			return imageMap.get("abstract_co.png");
		case FINAL:
			return imageMap.get("final_co.png");
		case STATIC:
			return imageMap.get("static_co.png");
		case TRANSIENT:
			return imageMap.get("transient_co.png");
		case SYNCHRONIZED:
			return imageMap.get("synch_co.png");
		case VOLATILE:
			return imageMap.get("volatile_co.png");
		case CONSTRUCTOR:
			return imageMap.get("constr_ovr.png");
			
		default:
			return null;
		}
	}
}
