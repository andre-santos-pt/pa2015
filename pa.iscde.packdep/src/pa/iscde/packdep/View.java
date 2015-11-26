package pa.iscde.packdep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

//Show package Dependencies in a window.
public class View implements PidescoView {

	
	//----Viewer----
	
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		// services
		JavaEditorServices editorService = Activator.getEditorService();
		ProjectBrowserServices projectService = Activator.getProjectService();
		
		// all packages
		ArrayList<PackageElement> packages = getAllPackages(projectService);
		
		//dependencies
		Multimap<PackageElement, PackageElement> dependencies = getDependencies(packages);
		
		//show graph of packages
		showPackDep(viewArea, imageMap, packages, dependencies);
		
	}
	
	
	//----Functions----

	
	// get all packages on the workspace
	private ArrayList<PackageElement> getAllPackages(ProjectBrowserServices projectService) {
		// all packages.
		ArrayList<PackageElement> packages = new ArrayList<PackageElement>();

		// root element of workspace.
		PackageElement root_package = projectService.getRootPackage();

		// if workspace is not empty.
		if (root_package.hasChildren()) {
			// childs of root_package.
			SortedSet<SourceElement> pack_child = root_package.getChildren();

			// when searching for packages, the childs, that are packages and
			// are not yet searched, go here.
			ArrayList<PackageElement> searchPackages = new ArrayList<PackageElement>();

			// check if a child is package
			//this gets only the packages that are children of the root_package.
			for (SourceElement element : pack_child) {
				if (element.isPackage()) {
					searchPackages.add((PackageElement) element);
					packages.add((PackageElement) element);
				}
			}

			//search all packages that are inside packages
			boolean over = false;
			ArrayList<PackageElement> newFoundPackages = new ArrayList<PackageElement>();
			while (!over) {
				for (PackageElement p : searchPackages) {
					if (p.hasChildren()) {
						// childs of packages
						SortedSet<SourceElement> children = p.getChildren();
						// check if a child is package
						for (SourceElement e : children) {
							if (e.isPackage()) {
								newFoundPackages.add((PackageElement) e);
								packages.add((PackageElement) e);
							}
						}
					}
				}
				// if there are new packages
				if (newFoundPackages.isEmpty()) {
					over = true;
				}
				// replace search array for the array with the new packages
				// found
				searchPackages = newFoundPackages;
				newFoundPackages = new ArrayList<PackageElement>();
			}
		}
		return packages;
	}

	//get package dependencies
	private Multimap<PackageElement, PackageElement> getDependencies(ArrayList<PackageElement> packages) {
		//Guava multimap
		//to associate each package with the packages that depends on.
		Multimap<PackageElement,PackageElement> map = ArrayListMultimap.create();
		
		//check files in each package to check the imports.
		for (PackageElement p : packages) {
			//if package contains children
			if(p.hasChildren()){
				SortedSet<SourceElement> c = p.getChildren();
				//check if child is a class
				for(SourceElement e : c){
					if(e.isClass()){
						//get imports of the class
						ArrayList<PackageElement> imports = getImports(e, packages);
						for(PackageElement pk : imports){
							map.put(p, pk);
						}
					}
				}
			}
		}
			
		return map;
	}
	
	private ArrayList<PackageElement> getImports(SourceElement e, ArrayList<PackageElement> packages){
		ArrayList<PackageElement> imports = new ArrayList<PackageElement>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(e.getFile()));
			String line;
			boolean stop = false;
			ArrayList<String> dependencies = new ArrayList<String>();
			while ((line = in.readLine()) != null && !stop) {
				// se e um import e nao a palavra import algures no
				// codigo.
				if (line.indexOf("import") == 0) {
					// retirar o import da string
					dependencies.add(line.replace("import ", ""));
				}
				// pode estar so assim porque e um else. Assim, mesmo
				// que haja
				// um import de algo que se chama class, isto nao para
				// de ler porque
				// e um import.
				else if (line.contains("class")) {
					stop = true;
				}
			}
			for (String string : dependencies) {
				String dependencie;
				//if import is not to a class that is on same package
				if(string.contains(".")){
				List<String> impo = Arrays.asList(string.split("\\."));
				dependencie = impo.get(impo.size()-2);
				//compare with packages names of the workspace
				for(PackageElement p : packages){
					if(p.getName().equals(dependencie) && !imports.contains(p)){
						imports.add(p);
					}
				}
				}
				
				
				
			}
		}
		catch (IOException exception) {}
		return imports;
		
	}
	
	//show graphically the package dependencies.
	private void showPackDep(Composite viewArea, Map<String, Image> imageMap, ArrayList<PackageElement> packages, Multimap<PackageElement, PackageElement> map){
		//layout
		viewArea.setLayout(new FillLayout());
		//set Graph
		Graph g = new Graph(viewArea, SWT.NONE);
		g.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println(((Graph) e.widget).getSelection());
			}
		});
		g.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		
		//packages.
		//Map each GraphNode to the respective package
		Map<PackageElement, GraphNode> graph = new HashMap<PackageElement, GraphNode>();
		for (PackageElement packageElement : packages) {
			GraphNode n1 = new GraphNode(g, SWT.NONE, packageElement.getName(), imageMap.get("icon.png"));
			graph.put(packageElement, n1);
		}
		
		//Dependencies
		for (PackageElement key : map.keySet()) {
		     Collection<PackageElement> values = map.get(key);
		     for (PackageElement p: values){
		    	 new GraphConnection(g, SWT.NONE, graph.get(key), graph.get(p));
		     }
		   }
		
		
		g.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
	}
	
	
	
	//---- examples ----
	
	
	//exemplo do zest
	private void zestExample(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new FillLayout());

		Image image1 = Display.getDefault().getSystemImage(SWT.ICON_INFORMATION);
		Image image2 = Display.getDefault().getSystemImage(SWT.ICON_WARNING);
		Image image3 = Display.getDefault().getSystemImage(SWT.ICON_ERROR);

		Graph g = new Graph(viewArea, SWT.NONE);
		g.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				System.out.println(((Graph) e.widget).getSelection());
			}

		});

		g.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		GraphNode n1 = new GraphNode(g, SWT.NONE, "Information", imageMap.get("icon.png"));
		GraphNode n2 = new GraphNode(g, SWT.NONE, "Warning", null);
		GraphNode n3 = new GraphNode(g, SWT.NONE, "Error", null);
		System.out.println(n3.getSize());

		new GraphConnection(g, SWT.NONE, n1, n2);
		new GraphConnection(g, SWT.NONE, n2, n3);

		g.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
	}

	
	//Exemplo para usar listener do editorServices.
	private void editorListenerExample(final JavaEditorServices service, final Text text) {
		service.addListener(new JavaEditorListener() {

			@Override
			public void fileOpened(File file) {
				try {
					BufferedReader in = new BufferedReader(new FileReader(file));
					String line;
					boolean stop = false;
					ArrayList<String> dependencies = new ArrayList<String>();
					while ((line = in.readLine()) != null && !stop) {
						// se e um import e nao a palavra import algures no
						// codigo.
						if (line.indexOf("import") == 0) {
							// retirar o import da string
							dependencies.add(line.replace("import ", ""));
						}
						// pode estar so assim porque e um else. Assim, mesmo
						// que haja
						// um import de algo que se chama class, isto nao para
						// de ler porque
						// e um import.
						else if (line.contains("class")) {
							stop = true;
						}
					}
					// print line todas as dependencias
					System.out.println("new file:");
					for (String string : dependencies) {
						System.out.println(string);
					}
				} catch (IOException e) {
				}
			}

			@Override
			public void fileSaved(File file) {
				// TODO Auto-generated method stub

			}

			@Override
			public void fileClosed(File file) {
				// TODO Auto-generated method stub

			}

			@Override
			public void selectionChanged(File file, String text, int offset, int length) {
				// TODO Auto-generated method stub

			}

		});
	}

	//Exemplo para usar o Listener do ProjectBrowserServices
	private void projectListenerExample(ProjectBrowserServices service, final Text text) {
		PackageElement root_package = service.getRootPackage();
		text.setText(root_package.getName());
		service.addListener(new ProjectBrowserListener() {

			@Override
			public void doubleClick(SourceElement element) {
				// nome do elemento
				// text.setText(element.getName());

				// nome do package onde o elemento esta se nao for ja um
				// package.
				if (element.isPackage() || element.isRoot()) {
					text.setText(element.getName());
					System.out.println(element.isRoot());
				} else {
					SourceElement el = element.getParent();
					while (!el.isPackage() && !el.isRoot()) {
						el = el.getParent();
					}
					text.setText(el.getName());
				}
			}

			@Override
			public void selectionChanged(Collection<SourceElement> selection) {
				// text.setText(selection.toString());
			}

		});
	}

}
