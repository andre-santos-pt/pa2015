package pt.iscde.classdiagram.model;

import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Image;

import pt.iscde.classdiagram.model.types.EChildElementType;
import pt.iscde.classdiagram.model.types.EModifierType;

public class AttributeElement extends ChildElementTemplate {

	private Map<String, Image> imageMap;

	public AttributeElement(Map<String, Image> imageMap) {
		super();
		this.imageMap = imageMap;
	}

	@Override
	public IFigure getLabel() {
		String labelText = String.format("%s: %s", getName(), getReturnType());
		Label label = new Label(labelText, getIcon());
		return label;
	}

	private Image getIcon() {

		Image image = null;

		if (getAccessControlType() != null) {

			switch (getAccessControlType()) {
			case PACKAGE:
				image = imageMap.get("field_default_obj.png");
				break;
			case PRIVATE:
				image = imageMap.get("field_private_obj.png");
				break;
			case PROTECTED:
				image = imageMap.get("field_protected_obj.png");
				break;
			case PUBLIC:
				image = imageMap.get("field_public_obj.png");
				break;
			default:
				return null;
			}
		} else {
			image = imageMap.get("field_default_obj.png");
		}

		if(getModifiers()!=null){
			int mergedOverlays = 0;
			for (EModifierType modifierType : getModifiers()) {
				Image overlayImg = EModifierType.getModifierIcon(modifierType, imageMap);
				image = getDecoratedImage(image, overlayImg, mergedOverlays++);
			}
		}
		return image;
	}

	@Override
	public EChildElementType getElementType() {
		return EChildElementType.Attribute;
	}

}
