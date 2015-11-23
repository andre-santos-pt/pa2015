package pt.iscde.classdiagram.model.zest;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IFigureProvider;
import org.eclipse.zest.core.viewers.ISelfStyleProvider;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import pt.iscde.classdiagram.model.TopLevelElement;

public class ClassDiagramLabelProvider extends LabelProvider implements ISelfStyleProvider, IFigureProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof EntityConnectionData) {
			EntityConnectionData data = (EntityConnectionData) element;

			if (data.dest instanceof TopLevelElement) {
				TopLevelElement destNode = (TopLevelElement) data.dest;

				switch (destNode.getClassType()) {
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
		return "";
	}
	
	@Override
	public void selfStyleNode(Object element, GraphNode node) {
		// TODO Auto-generated method stub
	}

	@Override
	public void selfStyleConnection(Object element, GraphConnection connection) {

		if (element instanceof EntityConnectionData) {
			EntityConnectionData data = (EntityConnectionData) element;

			if (data.dest instanceof TopLevelElement) {
				TopLevelElement destNode = (TopLevelElement) data.dest;

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
	public IFigure getFigure(Object element) {
		if (element instanceof TopLevelElement) {
			TopLevelElement topElement = (TopLevelElement) element;
			return topElement.getFigure();
		}

		return null;
	}
}
