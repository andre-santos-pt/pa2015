package pt.iscde.classdiagram.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import pt.iscde.classdiagram.model.ClassMethod;
import pt.iscde.classdiagram.model.ClassRepresentation;
import pt.iscde.classdiagram.model.EVisibility;
import pt.iscde.classdiagram.service.ClassDiagramServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ClassDiagramView implements PidescoView, ClassDiagramServices, ProjectBrowserListener, JavaEditorListener {
	private static final String VIEW_ID = "pt.iscte.pidesco.classdiagram";
	private static ClassDiagramView instance;
	private static PidescoServices pidescoServices;
	private static ProjectBrowserServices browserServices;
	private static JavaEditorServices javaEditorServices;
	
	
	private ClassRepresentation selClass;

	private Graph graph;

	public ClassDiagramView() {
		pidescoServices = ClassDiagramActivator.getInstance().getPidescoServices();
		browserServices = ClassDiagramActivator.getInstance().getBrowserServices();
		javaEditorServices = ClassDiagramActivator.getInstance().getJavaEditorServices();
		browserServices.addListener(this);
		javaEditorServices.addListener(this);
	}

	public static ClassDiagramView getInstance() {
		if (instance == null)
			pidescoServices.openView(VIEW_ID);

		return instance;
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		graph = new Graph(viewArea, SWT.BORDER);
	}

	@Override
	public void update(SourceElement sourceElement) {
//		for (GraphNode node : (List<GraphNode>)graph.getNodes()) {
//			node.dispose();
//		}
		
//		GraphNode node = new GraphNode(graph, SWT.NONE);
//		node.setText(sourceElement.getName());
	}

	List<String> metodos = new ArrayList<String>();

	@Override
	public void doubleClick(SourceElement element) {
		
		actualizaDiagrama(element.getFile());
		
	}

	private void actualizaDiagrama(File file) {
		limparGraph();
				
		selClass = new ClassRepresentation();
		IProblem[] parseFile = javaEditorServices.parseFile(file, new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration node) {
				selClass.setName(node.getName().getIdentifier());

				ITypeBinding typeBind = node.resolveBinding();
				ITypeBinding superTypeBind = typeBind.getSuperclass();
				ITypeBinding[] interfaceBinds = typeBind.getInterfaces();

				if(superTypeBind!=null){					
					ClassRepresentation superClass = new ClassRepresentation();
					superClass.setName(superTypeBind.getName());
					selClass.setSuperClass(superClass);
				}
				
				if(interfaceBinds!=null){
					for (ITypeBinding interfaceBind : interfaceBinds) {
						ClassRepresentation interfaceImplemented = new ClassRepresentation();
						interfaceImplemented.setName(interfaceBind.getName());
						selClass.getImplementedInterfaces().add(interfaceImplemented);
					}
				}
				
				
				return super.visit(node);
			}
			
			@Override
			public boolean visit(MethodDeclaration node) {

				final ClassMethod method = new ClassMethod();
				method.setName(node.getName().getIdentifier());
				
				// parâmetros
				for (Object object : node.parameters()) {
					if (object instanceof SingleVariableDeclaration) {
						SingleVariableDeclaration svd = (SingleVariableDeclaration) object;
						method.getParameterTypes().add(svd.getType().toString());
					}
				}
				
				if(node.getReturnType2()!=null){
					method.setReturnType(node.getReturnType2().toString());
				}
				
				// modifier
				node.accept(new ASTVisitor() {
					@Override
					public boolean visit(Modifier node) {
						if(node.isPublic())
							method.setVisibility(EVisibility.PUBLIC);
						else if(node.isPrivate())
							method.setVisibility(EVisibility.PRIVATE);
						else if(node.isProtected())
							method.setVisibility(EVisibility.PROTECTED);
						else
							method.setVisibility(EVisibility.PACKAGE);
						return super.visit(node);
					}

				});
				
				if(method.getVisibility() == null){
					method.setVisibility(EVisibility.PACKAGE);
				}
				selClass.getMethods().add(method);
				return super.visit(node);
			}
			
			
		});
		
		
		GraphNode mainNode = new GraphNode(graph, SWT.NONE);
		mainNode.setText(selClass.toString());
		
		for (ClassRepresentation interfaceRep : selClass.getImplementedInterfaces()) {
			GraphNode aNode = new GraphNode(graph, SWT.NONE);
			aNode.setText(interfaceRep.toString());
			GraphConnection connection = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED | ZestStyles.CONNECTIONS_DASH, mainNode, aNode);
			connection.setConnectionStyle(ZestStyles.CONNECTIONS_DASH);
		}
		
		
			GraphNode aNode = new GraphNode(graph, SWT.NONE);
			aNode.setText(selClass.getSuperClass().toString());
			GraphConnection connection = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED | ZestStyles.CONNECTIONS_DASH, mainNode, aNode);
			connection.setConnectionStyle(ZestStyles.CONNECTIONS_DASH);
	}

	private void limparGraph() {
		if(graph.getNodes()!=null){
		List<GraphNode> nodes = new ArrayList<>(graph.getNodes());
		  for(GraphNode n : nodes)
		     n.dispose();
		}
	}

	@Override
	public void selectionChanged(Collection<SourceElement> selection) {
		if (selection != null && !selection.isEmpty()) {
			for (SourceElement sourceElement : selection) {
				if (sourceElement.isClass()) {
					update(sourceElement);
					break;
				}
			}
		}

	}

	@Override
	public void fileOpened(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fileSaved(File file) {
		actualizaDiagrama(file);
		
	}

	@Override
	public void fileClosed(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		// TODO Auto-generated method stub
		
	}

}
