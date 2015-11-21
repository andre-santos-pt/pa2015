package pt.iscde.classdiagram.model.zest;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.zest.core.viewers.EntityConnectionData;

public class ClassDiagramLabelProvider extends LabelProvider {
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
			//EntityConnectionData test = (EntityConnectionData) element;
			return "";
		}
		throw new RuntimeException("Wrong type: " + element.getClass().toString());
	}
}