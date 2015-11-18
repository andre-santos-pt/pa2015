package pt.iscde.classdiagram.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import pt.iscde.classdiagram.model.ClassMethod;
import pt.iscde.classdiagram.model.ClassRepresentation;
import pt.iscde.classdiagram.model.EVisibility;
import pt.iscde.classdiagram.service.ClassDiagramServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ClassDiagramView implements PidescoView, ClassDiagramServices, ProjectBrowserListener {
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
		selClass = new ClassRepresentation();
		IProblem[] parseFile = javaEditorServices.parseFile(element.getFile(), new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration node) {
				selClass.setName(node.getName().getIdentifier());
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
				
				method.setReturnType(node.getReturnType2().toString());
				
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
		
		
		GraphNode node = new GraphNode(graph, SWT.NONE);
		node.setText(selClass.toString());
		
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

}
