package pa.iscde.outlaw.ivo;

import java.util.ArrayList;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import pa.iscde.outlaw.jorge.OutlineClass;
import pa.iscde.outlaw.jorge.OutlineField;
import pa.iscde.outlaw.jorge.OutlineMethod;
import pa.iscde.outlaw.jorge.OutlineRoot;

public class FileTreeContentProvider implements ITreeContentProvider  {

	private static final Object[] EMPTY_ARRAY = new Object[0];

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getChildren(Object arg0) {
		if(arg0 instanceof OutlineRoot ){
			return new Object[]{((OutlineRoot)arg0).getPackagezz(), ((OutlineRoot)arg0).getClazz()};
		}
		
		if(arg0 instanceof OutlineClass ){
			OutlineClass parent = (OutlineClass)arg0;
			ArrayList<Object> tmplist = new ArrayList<Object>(parent.getFields());
			tmplist.addAll(parent.getMethod());
			  return tmplist.toArray();
		}
		return EMPTY_ARRAY;
		
	}

	@Override
	public Object[] getElements(Object arg0) {
		return getChildren(arg0);
	}

	@Override
	public Object getParent(Object arg0) {
		if(arg0 instanceof OutlineClass || arg0 instanceof OutlineRoot)
			return null;
		if(arg0 instanceof OutlineMethod )
			return (Object)((OutlineMethod) arg0).getParent();
		if(arg0 instanceof OutlineField )
			return (Object)((OutlineField) arg0).getParent();
		return null;
	}

	@Override
	public boolean hasChildren(Object arg0) {
		return getChildren(arg0).length > 0;
	}

}
