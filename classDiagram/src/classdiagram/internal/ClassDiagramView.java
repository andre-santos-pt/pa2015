package classdiagram.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;

import classdiagram.service.ClassDiagramServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ClassDiagramView implements PidescoView, ClassDiagramServices, ProjectBrowserListener {
	private static final String VIEW_ID = "pt.iscte.pidesco.classDiagram";
	private static ClassDiagramView instance;
	private static PidescoServices pidescoServices;
	private static ProjectBrowserServices browserServices;
	private static JavaEditorServices javaEditorServices;

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
		for (GraphNode node : (List<GraphNode>)graph.getNodes()) {
			node.dispose();
		}
		
		GraphNode node = new GraphNode(graph, SWT.NONE);
		node.setText(sourceElement.getName());
	}

	List<String> metodos = new ArrayList<String>();

	@Override
	public void doubleClick(SourceElement element) {
		IProblem[] parseFile = javaEditorServices.parseFile(element.getFile(), new ASTVisitor() {

			@Override
			public boolean visit(FieldDeclaration node) {
				System.out.println(node.toString());

//				for (Object dec : node.fragments()) {
//					System.out.println(dec.toString());
//					System.out.println(dec.getClass());
//					if (dec instanceof VariableDeclarationFragment) {
//						VariableDeclarationFragment fd = (VariableDeclarationFragment) dec;
//						System.out.println(fd.toString());
//						System.out.println(fd.getParent().properties());
//					}
//				}
				return super.visit(node);
			}
			
			

			@Override
			public boolean visit(MethodDeclaration node) {
				System.out.print(node.toString());
				return super.visit(node);
			}

			@Override
			public boolean visit(Modifier node) {
				System.out.print(node.toString());
				return super.visit(node);
			}

		});

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
