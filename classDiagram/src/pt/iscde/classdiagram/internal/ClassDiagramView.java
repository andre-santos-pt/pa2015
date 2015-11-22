package pt.iscde.classdiagram.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
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

import pt.iscde.classdiagram.model.AttributeElement;
import pt.iscde.classdiagram.model.MethodElement;
import pt.iscde.classdiagram.model.MyConnection;
import pt.iscde.classdiagram.model.MyTopLevelElement;
import pt.iscde.classdiagram.model.types.EModifierType;
import pt.iscde.classdiagram.model.types.ETopElementType;
import pt.iscde.classdiagram.model.zest.ClassDiagramContentProvider;
import pt.iscde.classdiagram.model.zest.ClassDiagramLabelProvider;
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

	private GraphViewer viewer;
	private NodeModelContentProvider model;
	private Map<String, Image> imageMap;

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
		this.imageMap = imageMap;
		viewer = new GraphViewer(viewArea, SWT.BORDER);
		viewer.getGraphControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setContentProvider(new ClassDiagramContentProvider());
		viewer.setLabelProvider(new ClassDiagramLabelProvider());
		model = new NodeModelContentProvider();
		viewer.setInput(model.getNodes());
		LayoutAlgorithm layout = setLayout();
		viewer.setLayoutAlgorithm(layout, true);
		viewer.applyLayout();
	}

	private LayoutAlgorithm setLayout() {
		SpringLayoutAlgorithm springLayoutAlgorithm = new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		HorizontalShift horizontalShift = new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		return new CompositeLayoutAlgorithm(new LayoutAlgorithm[] { horizontalShift, springLayoutAlgorithm });
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

		javaEditorServices.parseFile(file, new ASTVisitor() {

			MyTopLevelElement mainNode = null;

			public boolean visit(EnumDeclaration node) {
				mainNode = new MyTopLevelElement(node.getName().getFullyQualifiedName(), node.getName().getIdentifier(),
						ETopElementType.ENUM, imageMap);
				model.getNodes().add(mainNode);
				return super.visit(node);
			}

			@Override
			public boolean visit(TypeDeclaration node) {
				mainNode = new MyTopLevelElement(node.getName().getFullyQualifiedName(), node.getName().getIdentifier(),
						node.isInterface() ? ETopElementType.INTERFACE : ETopElementType.CLASS, imageMap);
				model.getNodes().add(mainNode);

				ITypeBinding typeBind = node.resolveBinding();
				ITypeBinding superTypeBind = typeBind.getSuperclass();
				ITypeBinding[] interfaceBinds = typeBind.getInterfaces();

				if (superTypeBind != null) {
					MyTopLevelElement superNode = new MyTopLevelElement(superTypeBind.getQualifiedName(),
							superTypeBind.getName(), ETopElementType.CLASS, imageMap);
					model.getNodes().add(superNode);
					MyConnection connection = new MyConnection(null, "Extends", mainNode, superNode);
					model.addConnection(connection);
				}

				if (interfaceBinds != null) {
					for (ITypeBinding interfaceBind : interfaceBinds) {
						MyTopLevelElement interfaceNode = new MyTopLevelElement(interfaceBind.getQualifiedName(),
								interfaceBind.getName(), ETopElementType.INTERFACE, imageMap);
						model.getNodes().add(interfaceNode);

						MyConnection connection = new MyConnection(null, "Implements", mainNode, interfaceNode);
						model.addConnection(connection);
					}
				}

				// modifiers
				node.accept(new ASTVisitor() {
					@Override
					public boolean visit(Modifier node) {
						mainNode.addModifier(node);
						return super.visit(node);
					}

				});
				
				return super.visit(node);
			}

			// METHODS
			@Override
			public boolean visit(MethodDeclaration node) {

				final MethodElement method = new MethodElement(imageMap);
				method.setName(node.getName().getIdentifier());

				if (node.isConstructor()) {
					method.addModifier(EModifierType.CONSTRUCTOR);
				}

				// parâmetros
				for (Object object : node.parameters()) {
					if (object instanceof SingleVariableDeclaration) {
						SingleVariableDeclaration svd = (SingleVariableDeclaration) object;
						method.addParameter(svd.getType().toString());
					}
				}

				if (node.getReturnType2() != null) {
					method.setReturnType(node.getReturnType2().toString());
				}

				// modifiers
				node.accept(new ASTVisitor() {
					@Override
					public boolean visit(Modifier node) {
						method.addModifier(node);
						return super.visit(node);
					}

				});

				mainNode.addMethod(method);
				return super.visit(node);
			}

			// ATTRIBUTES
			@Override
			public boolean visit(FieldDeclaration node) {
				final AttributeElement attribute = new AttributeElement(imageMap);

				attribute.setReturnType(node.getType().toString());

				// internals
				node.accept(new ASTVisitor() {
					// modifiers
					@Override
					public boolean visit(Modifier node) {
						attribute.addModifier(node);
						return super.visit(node);
					}

					// name
					@Override
					public boolean visit(VariableDeclarationFragment node) {
						attribute.setName(node.getName().getIdentifier());
						return super.visit(node);
					}
				});

				mainNode.addAttribute(attribute);
				return super.visit(node);
			}

		});

		viewer.setInput(model.getNodes());

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
