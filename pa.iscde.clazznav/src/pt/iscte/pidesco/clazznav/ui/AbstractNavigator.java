package pt.iscte.pidesco.clazznav.ui;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;

public class AbstractNavigator {
	
	private Composite composite;
	public static ArrayList<File> files = new ArrayList<File>();
	
	public AbstractNavigator(Composite composite) {
		this.composite = composite;
	}

	public Composite getComposite() {
		return composite;
	}
	
	public void addFile(File file){
		files.add(file);
	}
		
	public void removeFile(File file){
		files.remove(file);
	}
}
