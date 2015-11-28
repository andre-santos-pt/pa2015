package pt.iscte.pidesco.clazznav.core;

import java.io.File;

import pt.iscte.pidesco.clazznav.ui.AbstractNavigator;
import pt.iscte.pidesco.clazznav.ui.GraphicNavigator;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class OpenFileListener extends JavaEditorListener.Adapter{

	//	private JavaEditorServices service;

	@Deprecated
	public OpenFileListener(JavaEditorServices service){
		//		this.service=service;
	}

	public OpenFileListener() {}
	
	@Override
	public void fileOpened(File file) {
		if(!AbstractNavigator.files.isEmpty()){
			if(!(AbstractNavigator.files.get(AbstractNavigator.files.size() - 1 ).getName().equals(file.getName()))){
				AbstractNavigator.files.add(file);
				GraphicNavigator.getInstance().refresh();
				System.out.println(AbstractNavigator.files.get(AbstractNavigator.files.size()-1).getName());
			}
		}
		else {
			AbstractNavigator.files.add(file);
//			GraphicNavigator.getInstance().refresh();
			System.out.println(AbstractNavigator.files.get(AbstractNavigator.files.size()-1).getName());
		}

	}

	@Override
	public void fileClosed(File file) {
		if(!AbstractNavigator.files.contains(file)){
			AbstractNavigator.files.add(file);
			System.out.println(AbstractNavigator.files.get(AbstractNavigator.files.size()-1).getName());
		}
	}




}
