package pt.iscde.classdiagram.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import pt.iscde.classdiagram.model.ClassRepresentation;
import pt.iscde.classdiagram.model.EClassType;
import pt.iscde.classdiagram.model.zest.ClassDiagramContentProvider;
import pt.iscde.classdiagram.model.zest.ClassDiagramLabelProvider;
import pt.iscde.classdiagram.model.zest.MyConnection;
import pt.iscde.classdiagram.model.zest.MyNode;
import pt.iscde.classdiagram.model.zest.NodeModelContentProvider;
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

	private GraphViewer viewer;
	private NodeModelContentProvider model;

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
		viewer = new GraphViewer(viewArea, SWT.BORDER);
		viewer.getGraphControl().setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		viewer.setContentProvider(new ClassDiagramContentProvider());
		viewer.setLabelProvider(new ClassDiagramLabelProvider(imageMap));
		model = new NodeModelContentProvider();
		viewer.setInput(model.getNodes());
		LayoutAlgorithm layout = setLayout();
		viewer.setLayoutAlgorithm(layout, true);
		viewer.applyLayout();
	}


	private LayoutAlgorithm setLayout() {
		SpringLayoutAlgorithm treeLayoutAlgorithm = new SpringLayoutAlgorithm(LayoutStyles.ENFORCE_BOUNDS);
		HorizontalShift horizontalShift = new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		return new CompositeLayoutAlgorithm(new LayoutAlgorithm[] { horizontalShift, treeLayoutAlgorithm });
	}

	@Override
	public void update(SourceElement sourceElement) {
		
	}

	List<String> metodos = new ArrayList<String>();

	@Override
	public void doubleClick(SourceElement element) {

		actualizaDiagrama(element.getFile());

	}

	private void actualizaDiagrama(File file) {
		model = new NodeModelContentProvider();
		
		IProblem[] parseFile = javaEditorServices.parseFile(file, new ASTVisitor() {

			MyNode mainNode = null;
			
			public boolean visit(EnumDeclaration node) {
				mainNode = new MyNode(node.getName().getFullyQualifiedName(), node.getName().getIdentifier(), EClassType.ENUM);				
				model.getNodes().add(mainNode);
				return super.visit(node);
			}
			
			
			@Override
			public boolean visit(TypeDeclaration node) {
				//selClass.setName(node.getName().getIdentifier());
				mainNode = new MyNode(node.getName().getFullyQualifiedName(), node.getName().getIdentifier(), node.isInterface()?EClassType.INTERFACE: EClassType.CLASS);				
				model.getNodes().add(mainNode);
				
				
				ITypeBinding typeBind = node.resolveBinding();
				ITypeBinding superTypeBind = typeBind.getSuperclass();
				ITypeBinding[] interfaceBinds = typeBind.getInterfaces();

				if (superTypeBind != null) {
					MyNode superNode = new MyNode(superTypeBind.getQualifiedName(), superTypeBind.getName(), EClassType.CLASS);
					model.getNodes().add(superNode);
					model.addConnection(new MyConnection(superTypeBind.getQualifiedName()+"::"+node.getName().getFullyQualifiedName(), "Extends", mainNode, superNode));
				}

				if (interfaceBinds != null) {
					for (ITypeBinding interfaceBind : interfaceBinds) {
						MyNode interfaceNode = new MyNode(interfaceBind.getQualifiedName(), interfaceBind.getName(), EClassType.INTERFACE);
						model.getNodes().add(interfaceNode);
						model.addConnection(new MyConnection(interfaceBind.getQualifiedName()+"::"+node.getName().getFullyQualifiedName(), "Implements", mainNode, interfaceNode));
					}
				}

				return super.visit(node);
			}

			
			
//			@Override
//			public boolean visit(MethodDeclaration node) {
//
//				final ClassMethod method = new ClassMethod();
//				method.setName(node.getName().getIdentifier());
//
//				// parâmetros
//				for (Object object : node.parameters()) {
//					if (object instanceof SingleVariableDeclaration) {
//						SingleVariableDeclaration svd = (SingleVariableDeclaration) object;
//						method.getParameterTypes().add(svd.getType().toString());
//					}
//				}
//
//				if (node.getReturnType2() != null) {
//					method.setReturnType(node.getReturnType2().toString());
//				}
//
//				// modifier
//				node.accept(new ASTVisitor() {
//					@Override
//					public boolean visit(Modifier node) {
//						if (node.isPublic())
//							method.setVisibility(EVisibility.PUBLIC);
//						else if (node.isPrivate())
//							method.setVisibility(EVisibility.PRIVATE);
//						else if (node.isProtected())
//							method.setVisibility(EVisibility.PROTECTED);
//						else
//							method.setVisibility(EVisibility.PACKAGE);
//						return super.visit(node);
//					}
//
//				});
//
//				if (method.getVisibility() == null) {
//					method.setVisibility(EVisibility.PACKAGE);
//				}
//				selClass.getMethods().add(method);
//				return super.visit(node);
//			}

		});

		viewer.setInput(model.getNodes());
		
		
//		GraphNode mainNode = new GraphNode(graph, SWT.NONE);
//		mainNode.setText(selClass.toString());
//
//		for (ClassRepresentation interfaceRep : selClass.getImplementedInterfaces()) {
//			GraphNode aNode = new GraphNode(graph, SWT.NONE);
//			aNode.setText(interfaceRep.toString());
//			GraphConnection connection = new GraphConnection(graph,
//					ZestStyles.CONNECTIONS_DIRECTED | ZestStyles.CONNECTIONS_DASH, mainNode, aNode);
//			connection.setConnectionStyle(ZestStyles.CONNECTIONS_DASH);
//		}
//
//		GraphNode aNode = new GraphNode(graph, SWT.NONE);
//		aNode.setText(selClass.getSuperClass().toString());
//		GraphConnection connection = new GraphConnection(graph,
//				ZestStyles.CONNECTIONS_DIRECTED | ZestStyles.CONNECTIONS_DASH, mainNode, aNode);
//		connection.setConnectionStyle(ZestStyles.CONNECTIONS_DASH);
	}

//	private void limparGraph() {
//		if (graph.getNodes() != null) {
//			List<GraphNode> nodes = new ArrayList<>(graph.getNodes());
//			for (GraphNode n : nodes)
//				n.dispose();
//		}
//	}

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
