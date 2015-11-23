package pt.iscde.classdiagram.model.zest;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import pt.iscde.classdiagram.model.MyTopLevelElement;

public class ClassDiagramContentProvider extends ArrayContentProvider implements IGraphEntityContentProvider{

	@Override
	public Object[] getConnectedTo(Object entity) {
		if (entity instanceof MyTopLevelElement) {
		      MyTopLevelElement node = (MyTopLevelElement) entity;
		      return node.getConnectedTo().toArray();
		    }
		    throw new RuntimeException("Type not supported");
	}
}
