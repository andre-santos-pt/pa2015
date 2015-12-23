package pa.iscde.templates.listener;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */

public class TemplatesListener extends JavaEditorListener.Adapter {
	private JavaEditorServices jeServices;
	
	public TemplatesListener(JavaEditorServices jeServices) {
		this.jeServices = jeServices;
	}

}
