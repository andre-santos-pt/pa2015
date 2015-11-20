package pt.iscte.pidesco.clazznav.utils;


/*
 * 
 *  A ser substituida pela lib do guava
 */
public class FilesUtil {

	public static String getFileExtension(String extension){
		int index = extension.lastIndexOf('.');
		if (index > 0) 
			return   extension = extension.substring(index+1);
		return "";
	}


}
