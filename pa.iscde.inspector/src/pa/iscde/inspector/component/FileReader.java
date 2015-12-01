package pa.iscde.inspector.component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

public class FileReader {
	
	public static final String PA2015ROOTNAME = "pa2015";
	public static final String PIDESCOROOTNAME = "pidesco";
	public static final String MANIFEST_FOLDER_NAME = "META-INF";
	public static final String PLUGIN_FILE_NAME = "plugin.xml";
	
	
	public static List<FilesToRead> getFilesPaths() {
		 List<FilesToRead> list = filesToReadPaths(getPA2015Path());
		 list.addAll(filesToReadPaths(getPidescoPath()));
		 return list;
	}

	private static ArrayList<FilesToRead> filesToReadPaths(String path) {
		ArrayList<FilesToRead> paths = new ArrayList<FilesToRead>();

		File file = new File(path);

		File[] files = file.listFiles();

		for (int i = 0; i < files.length; i++) {
			
			if (files[i].isDirectory()) {
				File[] files2 = files[i].listFiles();
				int numerOfFilesRead = 0;
				FilesToRead filesToRead = new FilesToRead();

				for (int j = 0; j < files2.length; j++) {
					if (files2[j].getName().equals(MANIFEST_FOLDER_NAME)) {
						filesToRead.setManifestPath(files2[j].listFiles()[0].getAbsolutePath());
						numerOfFilesRead++;
					}
					if(files2[j].getName().equals(PLUGIN_FILE_NAME)){
						filesToRead.setPluginXmlPath(files2[j].getAbsolutePath());
						numerOfFilesRead++;
					}
					if(numerOfFilesRead == 2){
						paths.add(filesToRead);
						break;
					}
				}
			}
		}
		return paths;
	}

	public static String getPA2015Path() {

		String path = FileReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		Iterable<String> splitted_string = Splitter.on("/").omitEmptyStrings().split(path);

		String final_string = "";
		for (String itr : splitted_string) {

			final_string += itr + "\\";
			if (itr.equals(PA2015ROOTNAME) ){
				break;
			}
		}
		return final_string;
	}
	
	public static String getPidescoPath() {

		String path = FileReader.class.getProtectionDomain().getCodeSource().getLocation().getPath();

		Iterable<String> splitted_string = Splitter.on("/").omitEmptyStrings().split(path);

		String final_string = "";
		for (String itr : splitted_string) {

			
			if (itr.equals(PA2015ROOTNAME) ){
				break;
			}
			final_string += itr + "\\";
		}
		
		File file = new File(final_string);
		File[] files = file.listFiles();
		
		for(int i = 0; i < files.length ; i++){
			if(files[i].getName().equals(PIDESCOROOTNAME)){
				final_string = files[i].getAbsolutePath();
				break;
			}
			
		}
		return final_string;
	}
	
//	public static void main(String[] args) {
//		List<FilesToRead> list = new FileReader().getFilesPaths();
//		
//		for (FilesToRead filesToRead : list) {
//			System.out.println(filesToRead.getManifestPath() + " " +filesToRead.getPluginXmlPath());
//		}
//	}
}
