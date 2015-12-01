package pa.iscde.inspector.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManifestParser {

	private String Name;
	private List<String> requiredBundle = new ArrayList<String>();
	private String ativator;
	private String symbolicName;

	public String getName() {
		return Name;
	}

	public String getAtivator() {
		return ativator;
	}


	
	public List<String> getRequiredBundle() {
		return requiredBundle;
	}

	public ManifestParser readFile(File file) {
		try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
			String line = br.readLine();
			while (line != null) {

				if (line.startsWith("Require-Bundle")) {
					line = fillRequiredBundleList(line, br);
				}
				if (line.startsWith("Bundle-Name")) {
					Name = line.substring(line.indexOf(' ')+1);
				} else if (line.startsWith("Bundle-Activator")) {
					ativator = line.split(" ")[1];
				}
				if(line.startsWith("Bundle-SymbolicName")){
					symbolicName = line.substring(line.indexOf(' ')+1,line.indexOf(';'));
				}
				line = br.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	private String fillRequiredBundleList( String line, BufferedReader br) throws IOException {
		
		requiredBundle.add(line.split(": ")[1].replaceAll(",", ""));
		String linha = br.readLine();
		while (linha != null) {
			if (!linha.contains(":")) {
				requiredBundle.add(linha.split(";")[0].replaceAll(" ", "").replaceAll(",", ""));
				linha = br.readLine();
			} else {
				line = linha;
				break;
			}
		}
		return line;
	}

	public String getSymbolicName() {
		return symbolicName;
	}

	
}
