package pa.iscde.outlaw.ivo;

import java.util.Map;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import pa.iscde.outlaw.jorge.OutlineClass;
import pa.iscde.outlaw.jorge.OutlineRoot;
import pa.iscde.outlaw.jorge.Visitor;

public class OutlineTreeView {

	private OutlineRoot root;
	private TreeViewer tv;
	
	public OutlineTreeView(Composite c, Visitor v, Map<String, Image> imageMap){

		root= new OutlineRoot();
		root.setClazz(v.getClazz());
		root.setPackagezz(v.getClazz().getPackagezz());
		tv = new TreeViewer(c, SWT.NONE);
		tv.setContentProvider(new FileTreeContentProvider());
		tv.setLabelProvider(new FileTreeLabelProvider(imageMap));
		
		tv.setInput(root);
		tv.expandAll();
	}
	
	public void update(OutlineClass clazz){
		root.setClazz(clazz);
		root.setPackagezz(clazz.getPackagezz());
		tv.setInput(root);
		tv.expandAll();
		
	}
	
	public OutlineTreeView(Composite c,Map<String, Image> imageMap ){
		root= new OutlineRoot();
		tv = new TreeViewer(c, SWT.NONE);
		tv.setContentProvider(new FileTreeContentProvider());
		tv.setLabelProvider(new FileTreeLabelProvider(imageMap));
	}

	public void clear() {
		tv.setInput(null);
	}
}
