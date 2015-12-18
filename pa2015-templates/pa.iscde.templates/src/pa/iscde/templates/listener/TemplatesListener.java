package pa.iscde.templates.listener;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;


public class TemplatesListener extends JavaEditorListener.Adapter {
	private JavaEditorServices jeServices;
	
	public TemplatesListener(JavaEditorServices jeServices) {
		this.jeServices = jeServices;
	}

}
