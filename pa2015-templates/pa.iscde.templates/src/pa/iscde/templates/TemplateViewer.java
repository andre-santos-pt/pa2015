package pa.iscde.templates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.templates.anotator.Ianotate;
import pa.iscde.templates.anotator.TemplateAnnotator;
import pa.iscde.templates.coding.DefaultReturns;
import pa.iscde.templates.coding.TemplateReturns;
import pa.iscde.templates.implementations.AbstractClass;
import pa.iscde.templates.implementations.Iimplement;
import pa.iscde.templates.implementations.Interface;
import pa.iscde.templates.model.ClassDeclaration;
import pa.iscde.templates.model.MethodDeclaration;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TemplateViewer implements PidescoView {
	//The external plugins required
	private JavaEditorServices _jeServices;
	private ProjectBrowserServices _pbservices;
	 
	private Tree arvoreInterfaces; 
	
	//Objetos para guardar as interfaces e classes abstratas e respetivas implementações
	//Foram divididas em duas por organização, não existe propriamente uma necessidade de estarem separadas.
	private Collection<Iimplement> _abstractClasses;
	private Collection<Iimplement> _interfaces;
	
	//Este objecto permite a costumização de código default quando é adicionado um novo metodo 
	//à classe pertencente a uma interface ou classe abstrata
	private TemplateReturns deafultCode;
	
	private Ianotate annotator;
	
	//Constructor
	public TemplateViewer() {
		deafultCode = new DefaultReturns();
		annotator = new TemplateAnnotator();
	}

	//Metodo para permitir a alteração do código default quando são adicionados os metodos
	public void SetDefaultReturns(TemplateReturns code)
	{
		deafultCode = code;
	}
	
	public void SetAnnotators(Ianotate annotator) {
		this.annotator = annotator;
	}
	
	//Reset do código default para o que criamos por omissão.
	public void ResetDefaultReturns() {
		deafultCode = new DefaultReturns();
	}
	
	//Cria os botões da interface e adiciona os listenners.
	private void CreateButtons(Composite viewArea)
	{
		final Button button = new Button(viewArea, SWT.PUSH);
		button.setLayoutData((new GridData(SWT.LEFT, SWT.TOP, false, false,-1 ,0)));
		button.setText("Refresh List");
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				RefreshTree();
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) { }
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {	}
		});
		
		final Button button2 = new Button(viewArea, SWT.PUSH);
		button2.setText("Implement current file");
		button2.setLayoutData((new GridData(SWT.RIGHT, SWT.TOP, false, false,-1 ,0)));
		button2.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
				ImplementCurrent();
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) { }
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {	}
		});
	}
	
	//Metodo que é invocado quando é feito duplo clique num item da treeview
	private void OpenThisSource(String element) {
		SourceElement toOpen = null;
		Collection<Iimplement> tmp =  null;
		tmp = _interfaces;
		tmp.addAll(_abstractClasses);
		for (Iimplement i : tmp)
		{
			if (i.getName().equals(element)) { toOpen = i.getSource(); break; }
			for (SourceElement s : i.getImplementations())
			{
				if (s.getName().replace(".java", "").equals(element)) {toOpen = i.getSource(); break;}
			}
		}
		if (toOpen != null)
			_jeServices.openFile(toOpen.getFile());
	}
	
	//Cria a arvore de interfaces e classes abstratas e adiciona o listenner para duplo clique
	private void CreateTree(Composite viewArea)
	{
		arvoreInterfaces = new Tree(viewArea, SWT.NULL);
		arvoreInterfaces.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1 ,1));
		arvoreInterfaces.addListener(SWT.MouseDoubleClick, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				Point point = new Point(arg0.x, arg0.y);
				TreeItem item = arvoreInterfaces.getItem(point);
				if (item != null) {
					OpenThisSource(item.getText());
				}
			}
		});
	}
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		//Get pidesco services
		_jeServices = Activator.getActivator().getJavaEditorservice(); 
		_pbservices = Activator.getActivator().getProjectBrowserServices();	
		viewArea.setLayout(new GridLayout(1,false));
		CreateButtons(viewArea);
		CreateTree(viewArea);
		RefreshTree();
	}
	
	//Obtem a lista de sources do project browser
	private SortedSet<SourceElement> getSourceItems()
	{
		return _pbservices.getRootPackage().getChildren();
	}
	
	//Adiciona linhas de código ao editor, retorna a linha onde ficou
	private int addToEditor(String txt, int linha)
	{
		_jeServices.insertLine(_jeServices.getOpenedFile(), txt, linha);
		int currentLine = linha + txt.split("\n").length; 
		return currentLine;
	}
	
	private boolean addinclude(ClassDeclaration cd, String inc, int linha)
	{
		if (inc.length()<=0) return false;
		boolean add=true;
		for (String i : cd.includes)
		{
			if (i.equals(inc))
			{
				add=false;
				break;
			}
		}
		if (add)
		{
			addToEditor(inc+"\n", linha);
		}
		return add;
	}
	
	//Esta é a função que permite implementar as interfaces e classes abstratas
	public void ImplementCurrent()
	{
		_jeServices.saveFile(_jeServices.getOpenedFile());
		ClassDeclaration declararion = new ClassDeclaration(_jeServices.getOpenedFile());
		if (!declararion.isImplementingInterface() && !declararion.isImplementingAbstract()) return;
		Iimplement toImplement = null;
		Collection<Iimplement> tmp =  null;
		if (declararion.isImplementingInterface()) tmp = _interfaces;
		else if (declararion.isImplementingAbstract()) tmp = _abstractClasses;
		
		for (Iimplement i : tmp)
		{
			if (i.getName().equals(declararion.getTargetForImplement()))
			{
				toImplement = i;
				break;
			}
		}

		Collection<MethodDeclaration> missingMethods = toImplement.implement(_jeServices.getOpenedFile());
		int lineToWrite = 2;
		
		for (String inc : annotator.getIncludes())
		{
			if (addinclude(declararion, inc, lineToWrite)) lineToWrite++;
		}
		
		lineToWrite = lineToWrite+ declararion.lines - 1;
		
		for (MethodDeclaration md : missingMethods)
		{
			for (String s : annotator.getAnotations())
			{
				lineToWrite = addToEditor("\t"+s, lineToWrite);
			}
			lineToWrite = addToEditor("\t"+md.getSignature(), lineToWrite);
			lineToWrite = addToEditor( "\t\t"+deafultCode.textForReturn(md).replace("\n", "\n\t\t"), lineToWrite);
			lineToWrite = addToEditor("\t}", lineToWrite);
			lineToWrite = addToEditor(" ", lineToWrite);
		}
		_jeServices.saveFile(_jeServices.getOpenedFile());
		RefreshTree();
	}
	
	//Cria um item novo na treeview
	private TreeItem createTreeElement(TreeItem root, String nodeText)
	{
		TreeItem i = new TreeItem(root, SWT.NULL);
		i.setText(nodeText.replace(".java", ""));
		return i;
	}
	
	//Este metodo procura as classes que implementam ou extendem as interfaces e classes abstratas
	private void addSubElements(String mask, TreeItem root, String clazz, SortedSet<SourceElement> from, Iimplement it )
	{
		for ( SourceElement s : from )
		{
			if (s.isClass())
			{
				ClassDeclaration declaration = new ClassDeclaration(s);
				if (declaration.fullDeclaration.contains(mask))
				{
					String target = declaration.getTargetForImplement();
					if (target.equals(clazz))
					{
						it.addImplementation(s);
						createTreeElement (root,s.getName().replace(".java", "") );
					}
				}
			}
			else if (s.isPackage())
			{
				PackageElement pe = (PackageElement)s;
				addSubElements(mask, root, clazz, pe.getChildren(), it);
			}
		}
	}
	
	//Procura pelas interfaces e classes abstratas existentes
	private void addElements(TreeItem rootI, TreeItem rootA, SortedSet<SourceElement> from)
	{
		for ( SourceElement s : from )
		{
			if (s.isClass())
			{
				ClassDeclaration declaration = new ClassDeclaration(s);
				if (declaration.isInterface()){
				  TreeItem i = createTreeElement (rootI,s.getName().replace(".java", "") );
				  Interface it = new Interface(s);
				  addSubElements("implements", i, s.getName().replace(".java", ""), getSourceItems(), it);
				  _interfaces.add(it);
				}
				else if (declaration.isAbstractDeclaration()) {
				  TreeItem i = createTreeElement (rootA,s.getName().replace(".java", "") );
				  AbstractClass it = new AbstractClass(s);
				  addSubElements("extends", i, s.getName().replace(".java", ""), getSourceItems(), it);
				  _abstractClasses.add(it);
				}
			}
			else if (s.isPackage())
			{
				PackageElement pe = (PackageElement)s;
				addElements(rootI, rootA, pe.getChildren());
			}
		}
	}
	
	//refresca a treeView
	public void RefreshTree()
	{
		arvoreInterfaces.removeAll();
		TreeItem RootInterfaces = new TreeItem(arvoreInterfaces, SWT.NULL);
		RootInterfaces.setText("Interfaces");
		_interfaces = new ArrayList<>();
		
		TreeItem RootAbstracts = new TreeItem(arvoreInterfaces, SWT.NULL);
		RootAbstracts.setText("Abstracts");
		_abstractClasses = new ArrayList<>();
		
		addElements(RootInterfaces, RootAbstracts, getSourceItems());
	}
	
	//metodo para devolver que classes implementam uma interface
	public Collection<SourceElement> GetImplementationsOF (SourceElement elementInterface)
	{
		if (! elementInterface.isClass()) return new ArrayList<>();
		ClassDeclaration declaration = new ClassDeclaration(elementInterface);
		if (!declaration.isInterface()) return new ArrayList<>();
		for (Iimplement i : _interfaces)
		{
			if (i.getName().equals(elementInterface.getName().replace(".java", "")))
				return i.getImplementations();
		}
		return new ArrayList<>();
	}

	//metodo para devolver que classes extendem uma classe abstratra
	public Collection<SourceElement> GetExtendsOF (SourceElement elementInterface)
	{
		if (! elementInterface.isClass()) return new ArrayList<>();
		ClassDeclaration declaration = new ClassDeclaration(elementInterface);
		if (!declaration.isAbstractDeclaration()) return new ArrayList<>();
		for (Iimplement i : _abstractClasses)
		{
			if (i.getName().equals(elementInterface.getName().replace(".java", "")))
				return i.getImplementations();
		}
		return new ArrayList<>();
	}
	
	//metodo para devolver que classes implementam ou extendem algo
	public Collection<SourceElement> GetImplementationsOF (Iimplement element) {
		return element.getImplementations();
	}
	
	//metodo para devolver todas as interfaces existentes
	public Collection<SourceElement> GetAllInterfaces()
	{
		Collection<SourceElement> tmp = new ArrayList<>();
		for (Iimplement i : _interfaces)
			tmp.add(i.getSource());
		return tmp;
	}
	
	//metodo para devolver todas as classes abstratas existentes
	public Collection<SourceElement> GetAllAbstracts()
	{
		Collection<SourceElement> tmp = new ArrayList<>();
		for (Iimplement i : _abstractClasses)
			tmp.add(i.getSource());
		return tmp;
	}
}
