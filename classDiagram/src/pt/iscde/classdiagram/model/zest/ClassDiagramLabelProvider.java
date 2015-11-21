package pt.iscde.classdiagram.model.zest;

import java.util.Map;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IFigureProvider;
import org.eclipse.zest.core.viewers.ISelfStyleProvider;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import pt.iscde.classdiagram.ui.UMLClassFigure;

public class ClassDiagramLabelProvider extends LabelProvider implements ISelfStyleProvider, IFigureProvider {

	private Map<String, Image> imageMap;

	public ClassDiagramLabelProvider(Map<String, Image> imageMap) {
		this.imageMap = imageMap;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof MyNode) {
			MyNode myNode = (MyNode) element;
			return getClassIcon(myNode);
		}
		return null;
	}

	private Image getClassIcon(MyNode myNode) {
		switch (myNode.getClassType()) {
		case ABSTRACT_CLASS:
		case CLASS:
			return imageMap.get("class_obj.png");
		case INTERFACE:
			return imageMap.get("int_obj.png");
		case ENUM:
			return imageMap.get("enum_obj.png");
		default:
			return null;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof MyNode) {
			MyNode myNode = (MyNode) element;
			return myNode.getName();
		}
		// Not called with the IGraphEntityContentProvider
		if (element instanceof MyConnection) {
			MyConnection myConnection = (MyConnection) element;
			return myConnection.getLabel();
		}

		if (element instanceof EntityConnectionData) {
			EntityConnectionData data = (EntityConnectionData) element;

			if (data.dest instanceof MyNode) {
				MyNode destNode = (MyNode) data.dest;

				switch (destNode.getClassType()) {
				case ABSTRACT_CLASS:
					return "Extends";
				case CLASS:
					return "Extends";
				case INTERFACE:
					return "Implements";
				default:
					return "";
				}
			}

			return "SAY WHAT?!!";
		}
		throw new RuntimeException("Wrong type: " + element.getClass().toString());
	}

	@Override
	public void selfStyleConnection(Object element, GraphConnection connection) {

		if (element instanceof EntityConnectionData) {
			EntityConnectionData data = (EntityConnectionData) element;

			if (data.dest instanceof MyNode) {
				MyNode destNode = (MyNode) data.dest;

				switch (destNode.getClassType()) {
				case INTERFACE:
					connection.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
					connection.setLineStyle(Graphics.LINE_DASH);
					break;

				default:
					connection.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
					connection.setLineStyle(Graphics.LINE_SOLID);
					break;
				}

			}
		}

	}

	@Override
	public void selfStyleNode(Object element, GraphNode node) {
		
	}

	private IFigure generateClassFigure(MyNode node) {
		Label classLabel2 = new Label(node.getName(), getClassIcon(node));
		classLabel2.setFont(new Font(null, "Arial", 12, SWT.BOLD));

		UMLClassFigure classFigure = new UMLClassFigure(classLabel2);
		Label attribute3 = new Label("columnID: int", null);
		Label attribute4 = new Label("items: List", null);

		Label method3 = new Label("getColumnID(): int", null);
		Label method4 = new Label("getItems(): List", null);

		classFigure.getAttributesCompartment().add(attribute3);
		classFigure.getAttributesCompartment().add(attribute4);
		classFigure.getMethodsCompartment().add(method3);
		classFigure.getMethodsCompartment().add(method4);
		classFigure.setSize(-1, -1);

		return classFigure;
	}
	
	/**
	 * Merges 2 images so they appear beside each other
	 * 
	 * You must dispose this image!
	 * @param image1
	 * @param image2
	 * @param result
	 * @return
	 */
	public static Image mergeImages(Image image1, Image image2) {
		Image mergedImage = new Image(Display.getDefault(), image1.getBounds().width + image2.getBounds().width, image1.getBounds().height);
		GC gc = new GC(mergedImage);
		gc.drawImage(image1, 0, 0);
		gc.drawImage(image2, image1.getBounds().width, 0);
		gc.dispose();
		return mergedImage;
	}

	@Override
	public IFigure getFigure(Object element) {
		if (element instanceof MyNode) {
			return generateClassFigure((MyNode) element);
		}
		
		return null;
	}
	
}
