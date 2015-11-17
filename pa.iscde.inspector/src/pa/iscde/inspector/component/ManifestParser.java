package pa.iscde.inspector.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManifestParser {

	private String Name;
	private List<String> extensionsPoints;
	private List<String> extensions = new ArrayList<String>();
	private String ativator;
	private File file;

	public String getName() {
		return Name;
	}


	public List<String> getExtensionsPoints() {
		return extensionsPoints;
	}


	public List<String> getExtensions() {
		return extensions;
	}

	public String getAtivator() {
		return ativator;
	}

	public void setAtivator(String ativator) {
		this.ativator = ativator;
	}

	public ManifestParser(File file) {
		this.file = file;
	}

	public void readFile() {

		// try {
		// String linesepator = System.getProperty("line.separator");
		// String alltext = new String(Files.readAllBytes(file.toPath()));
		// Iterable<String> splitter = Splitter.on(": ").split(alltext);
		// for (String string : splitter) {
		// System.out.println(string);
		// switch (string) {
		// case "Bundle-Name":
		// Name = string.split(": ")[1];
		// break;
		// case "Bundle-Activator":
		// ativator = string.split(": ")[1];
		// break;
		// default:
		// break;
		// }
		// }
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
			String line = br.readLine();
			boolean read = true;
			while (line != null) {

				if (line.startsWith("Require-Bundle")) {
					line = fillExtensionList(line, br);
					read = false;
				}
				if (line.startsWith("Bundle-Name")) {
					Name = line.split(" ")[1];
				} else if (line.startsWith("Bundle-Activator")) {
					ativator = line.split(" ")[1];
				}
				line = br.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String fillExtensionList( String line, BufferedReader br) throws IOException {
		
		extensions.add(line.split(": ")[1].replaceAll(",", ""));
		String linha = br.readLine();
		while (linha != null) {
			if (!linha.contains(":")) {
				extensions.add(linha.split(";")[0].replaceAll(" ", ""));
				linha = br.readLine();
			} else {
				line = linha;
				break;
			}
		}
		return line;
	}

	public static void main(String[] args) {
		FileReader fr = new FileReader();
		fr.getAllComponents();

		for (String string : fr.getManifests()) {
				System.out.println(string);
				ManifestParser mp = new ManifestParser(new File(string));

				mp.readFile();
				System.out.println("Name: " + mp.getName());
				System.out.println("Ativator: " + mp.getAtivator());

				System.out.print("Extensões: ");
				for (String a : mp.extensions) {
					System.out.print(a + " ");
				}
				System.out.println("Fim");
			
		}
	}
}
