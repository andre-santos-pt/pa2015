package pt.iscde.classdiagram.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Image;

import pt.iscde.classdiagram.model.types.EChildElementType;
import pt.iscde.classdiagram.model.types.EModifierType;

public class MethodElement extends ChildElementTemplate {

	private Map<String, Image> imageMap;
	private List<String> parameterTypes;

	public MethodElement(Map<String, Image> imageMap) {
		super();
		this.imageMap = imageMap;
		this.parameterTypes = new ArrayList<String>();
	}

	public void addParameter(String parameterType) {
		parameterTypes.add(parameterType);
	}

	@Override
	public IFigure getLabel() {
		String labelText = "";
		if (getModifiers().contains(EModifierType.CONSTRUCTOR)) {
			labelText = String.format("%s (%s)", getName(), getParameterTypesString());
		} else {
			labelText = String.format("%s (%s): %s", getName(), getParameterTypesString(), getReturnType());
		}

		Label label = new Label(labelText, getIcon());
		return label;

	}

	private String getParameterTypesString() {
		StringBuilder sb = new StringBuilder();
		if(parameterTypes!=null){
			for (int i = 0; i < parameterTypes.size(); i++) {
				if(i>0)
					sb.append(", ");
				sb.append(parameterTypes.get(i));
			}
		}
		return sb.toString();
	}

	private Image getIcon() {

		Image image = null;

		switch (getAccessControlType()) {
		case PACKAGE:
			image = imageMap.get("methdef_obj.png");
			break;
		case PRIVATE:
			image = imageMap.get("methpri_obj.png");
			break;
		case PROTECTED:
			image = imageMap.get("methpro_obj.png");
			break;
		case PUBLIC:
			image = imageMap.get("methpub_obj.png");
			break;
		default:
			return null;
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
		return EChildElementType.Method;
	}

	
}
