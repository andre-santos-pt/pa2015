package pt.iscde.classdiagram.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import pt.iscde.classdiagram.model.types.EChildElementType;
import pt.iscde.classdiagram.model.types.EModifierType;

public abstract class ChildElementTemplate {

	private String name;
	private EModifierType accessControlType;
	private Set<EModifierType> modifiers;
	private String returnType;

	public ChildElementTemplate() {
		super();
		this.modifiers = new HashSet<EModifierType>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EModifierType getAccessControlType() {
		return accessControlType;
	}

	public void setAccessControlType(EModifierType accessControlType) {
		this.accessControlType = accessControlType;
	}

	protected Set<EModifierType> getModifiers() {
		return modifiers;
	}

	public void addModifier(EModifierType modifierType) {
		modifiers.add(modifierType);
	}

	public void addModifier(Modifier node) {
		// Set Access ControlType
		if (node.isPublic())
			setAccessControlType(EModifierType.PUBLIC);
		else if (node.isPrivate())
			setAccessControlType(EModifierType.PRIVATE);
		else if (node.isProtected())
			setAccessControlType(EModifierType.PROTECTED);
		else if (node.isDefault())
			setAccessControlType(EModifierType.PACKAGE);

		// Add modifiers ControlType
		if (node.isAbstract()) {
			getModifiers().add(EModifierType.ABSTRACT);
		}
		if (node.isFinal()) {
			getModifiers().add(EModifierType.FINAL);
		}
		if (node.isStatic()) {
			getModifiers().add(EModifierType.STATIC);
		}
		if (node.isTransient()) {
			getModifiers().add(EModifierType.TRANSIENT);
		}
		if (node.isSynchronized()) {
			getModifiers().add(EModifierType.SYNCHRONIZED);
		}
		if (node.isVolatile()) {
			getModifiers().add(EModifierType.VOLATILE);
		}
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public static Image getDecoratedImage(final Image image, final Image overlay, final int num) {
		Image decoratedImage = null;
		if (decoratedImage == null) {
			CompositeImageDescriptor cd = new CompositeImageDescriptor() {
				@Override
				protected Point getSize() {
					Rectangle bounds = image.getBounds();
					return new Point(bounds.width, bounds.height);
				}

				@Override
				protected void drawCompositeImage(int width, int height) {
					drawImage(image.getImageData(), 0, 0);
					if (num == 0)
						drawImage(overlay.getImageData(), 0, 0);
					else if (num == 1) {
						int ox = image.getBounds().width - overlay.getBounds().width;
						int oy = 0;
						drawImage(overlay.getImageData(), ox, oy);
					} else if (num == 2) {
						int ox = 0;
						int oy = image.getBounds().height - overlay.getBounds().height;
						drawImage(overlay.getImageData(), ox, oy);
					} else if (num == 3) {
						int ox = image.getBounds().width - overlay.getBounds().width;
						int oy = image.getBounds().height - overlay.getBounds().height;
						drawImage(overlay.getImageData(), ox, oy);
					} else {
						int ox = (image.getBounds().width - overlay.getBounds().width) / 2;
						int oy = (image.getBounds().height - overlay.getBounds().height) / 2;
						drawImage(overlay.getImageData(), ox, oy);
					}
				}
			};
			decoratedImage = cd.createImage();
		}
		return decoratedImage;
	}

	public abstract EChildElementType getElementType();

	public abstract IFigure getLabel();
}
