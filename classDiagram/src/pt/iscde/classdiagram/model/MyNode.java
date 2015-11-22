package pt.iscde.classdiagram.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import pt.iscde.classdiagram.model.types.EModifierType;
import pt.iscde.classdiagram.model.types.ETopElementType;
import pt.iscde.classdiagram.ui.UMLClassFigure;

public class MyNode implements TopLevelElement{
	private final String id;
	private final String name;
	private final ETopElementType classType;
	private List<TopLevelElement> connections;
	private List<ChildElementTemplate> attrinutes;
	private List<ChildElementTemplate> methods;
	private Set<EModifierType> modifiers;
	
	private Map<String, Image> imageMap;

	public MyNode(String id, String name, ETopElementType classType, Map<String, Image> imageMap) {
		this.id = id;
		this.name = name;
		this.classType = classType;
		this.imageMap = imageMap;

		this.connections = new ArrayList<TopLevelElement>();
		this.attrinutes = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.modifiers = new HashSet<EModifierType>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public ETopElementType getClassType(){
		return classType;
	}

	@Override
	public List<TopLevelElement> getConnectedTo() {
		return connections;
	}

	@Override
	public void addAttribute(ChildElementTemplate childElement) {
		this.attrinutes.add(childElement);
	}

	@Override
	public void addMethod(ChildElementTemplate childElement) {
		this.methods.add(childElement);
	}

	@Override
	public IFigure getFigure() {
		Label classLabel = new Label(getName(), getClassIcon());
		classLabel.setFont(new Font(null, "Arial", 12, SWT.BOLD));

		UMLClassFigure classFigure = new UMLClassFigure(classLabel);
		
		if(attrinutes!=null && attrinutes.size() > 0){
			for (ChildElementTemplate childElement : attrinutes) {
				classFigure.getAttributesCompartment().add(childElement.getLabel());
			}
		}else{
			classFigure.getAttributesCompartment().add(new Label("", null));
		}
		
		if(methods!=null && methods.size() > 0){
			for (ChildElementTemplate childElement : methods) {
				classFigure.getMethodsCompartment().add(childElement.getLabel());
			}
		}else{
			classFigure.getMethodsCompartment().add(new Label("", null));
		}
		
		classFigure.setSize(-1, -1);

		return classFigure;
	}

	private Image getClassIcon() {
		Image image = null;
		
		switch (getClassType()) {
		case CLASS:
			image = imageMap.get("class_obj.png");
			break;
		case INTERFACE:
			image =  imageMap.get("int_obj.png");
			break;
		case ENUM:
			image =  imageMap.get("enum_obj.png");
			break;
		default:
			return null;
		}
		
		//Add modifier overlays
		if(modifiers!=null){
			int mergedOverlays = 0;
			for (EModifierType modifierType : modifiers) {
				Image overlayImg = EModifierType.getModifierIcon(modifierType, imageMap);
				image = ChildElementTemplate.getDecoratedImage(image, overlayImg, mergedOverlays++);
			}
		}
		return image;
	}

	@Override
	public void addMmodifier(EModifierType modifierType) {
		modifiers.add(modifierType);
	}

	
	
}
