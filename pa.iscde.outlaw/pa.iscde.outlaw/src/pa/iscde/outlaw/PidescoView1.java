package pa.iscde.outlaw;

import java.io.File;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import pa.iscde.outlaw.ivo.OutlineTreeView;
import pa.iscde.outlaw.jorge.Visitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.internal.JavaEditorActivator;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class PidescoView1 implements PidescoView {

	private Visitor v;
	private Composite viewArea;
	private Map<String, Image> imageMap;
	private OutlineTreeView otv;
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		
		this.setViewArea(viewArea);
		this.setImageMap(imageMap);
		viewArea.setBackground(viewArea.getDisplay().getSystemColor(SWT.COLOR_WHITE));

		final JavaEditorServices services = JavaEditorActivator.getInstance().getServices();
		
		final File f = services.getOpenedFile();
		
		if(f!=null){
			v= new Visitor(f);
			services.parseFile(f, v);
			otv = new OutlineTreeView(viewArea,v,imageMap);
		}else{
			otv = new OutlineTreeView(viewArea,imageMap);
		}
			
		services.addListener(new JavaEditorListener.Adapter() {

			@Override
			public void fileOpened(File file) {

				if(v==null){
					v= new Visitor(file);
					
				}
				
				if(!v.equals(null)){

					v.setFile(file);
					v.setParentClass(file.getName());
					services.parseFile(file, v);

					otv.update(v.getClazz());
				}

			}
			
			@Override
			public void fileClosed(File file) {
				
				//otv.clear();
			}
			
			@Override
			public void fileSaved(File file) {
				super.fileSaved(file);
				v.setFile(file);
				v.setParentClass(file.getName());
				services.parseFile(file, v);
				otv.update(v.getClazz());
			}
		});

		
	}

	private void setImageMap(Map<String, Image> imageMap) {
		this.imageMap=imageMap;
	}

	private void setViewArea(Composite viewArea) {
		this.viewArea=viewArea;
	}

	public Composite getViewArea() {
		return viewArea;
	}

	public Map<String, Image> getImageMap() {
		return imageMap;
	}
}