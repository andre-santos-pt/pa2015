package pt.iscte.pidesco.clazznav.core;

import java.io.File;

import pt.iscte.pidesco.clazznav.service.ClazzNavServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;;
/**
 * 
 * @author tiagocms
 *
 */
public class BuilderListener extends JavaEditorListener.Adapter{
	
	/**
	 * 
	 * 
	 */

	ClazzNavServices services;
	
	/**
	 * 
	 */
	public BuilderListener(){
		//TODO
	}
	
	/**
	 * 
	 */
	@Override
	public void fileOpened(File file) {
		//TODO
	}



}
