package pt.iscde.classdiagram.internal;

import java.io.File;
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
import org.eclipse.swt.graphics.Image;

import pt.iscde.classdiagram.model.AttributeElement;
import pt.iscde.classdiagram.model.MethodElement;
import pt.iscde.classdiagram.model.MyConnection;
import pt.iscde.classdiagram.model.MyTopLevelElement;
import pt.iscde.classdiagram.model.types.EModifierType;
import pt.iscde.classdiagram.model.types.ETopElementType;
import pt.iscde.classdiagram.model.zest.NodeModelContentProvider;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class ClassDiagramElementVisitor extends ASTVisitor {


	private MyTopLevelElement mainNode = null;
	private Map<String, Image> imageMap;
	private NodeModelContentProvider model;
	private static PidescoServices pidescoServices;
	private static JavaEditorServices javaEditorServices;
	private File foundFile;

	
	public MyTopLevelElement getMainNode() {
		return mainNode;
	}

	public void setMainNode(MyTopLevelElement mainNode) {
		this.mainNode = mainNode;
	}

	public Map<String, Image> getImageMap() {
		return imageMap;
	}

	public void setImageMap(Map<String, Image> imageMap) {
		this.imageMap = imageMap;
	}

	public NodeModelContentProvider getModel() {
		return model;
	}

	public void setModel(NodeModelContentProvider model) {
		this.model = model;
	}

	public ClassDiagramElementVisitor(Map<String, Image> imageMap,
			NodeModelContentProvider model) {
		super();
		this.imageMap = imageMap;
		this.model = model;
		pidescoServices = ClassDiagramActivator.getInstance().getPidescoServices();
		javaEditorServices = ClassDiagramActivator.getInstance().getJavaEditorServices();
	}

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

		if (superTypeBind != null && !(superTypeBind.getName().equals("Object"))) {
			MyTopLevelElement superNode = new MyTopLevelElement(superTypeBind.getQualifiedName(),superTypeBind.getName(), ETopElementType.CLASS, imageMap);
			//model.getNodes().add(superNode);
			

			findClassFile(pidescoServices.getWorkspaceRoot().getParentFile().getParentFile(), superTypeBind.getQualifiedName());
			if(foundFile != null){
				ClassDiagramElementVisitor classDiagramElementVisitor = new ClassDiagramElementVisitor(imageMap, model);
				javaEditorServices.parseFile(foundFile, classDiagramElementVisitor);
				//MyConnection connection = new MyConnection(null, "Extends", mainNode, classDiagramElementVisitor.getMainNode());
				//model.addConnection(connection);
				mainNode.getConnectedTo().add(classDiagramElementVisitor.getMainNode());
				
			}
			else{
				MyConnection connection = new MyConnection(null, "Extends", mainNode, superNode);
				model.addConnection(connection);
			}
		}

		if (interfaceBinds != null) {
			for (ITypeBinding interfaceBind : interfaceBinds) {
				MyTopLevelElement interfaceNode = new MyTopLevelElement(interfaceBind.getQualifiedName(), interfaceBind.getName(), ETopElementType.INTERFACE, imageMap);
				//model.getNodes().add(interfaceNode);

				findClassFile(pidescoServices.getWorkspaceRoot().getParentFile().getParentFile(), interfaceBind.getQualifiedName());
				if(foundFile != null){
					ClassDiagramElementVisitor classDiagramElementVisitor = new ClassDiagramElementVisitor(imageMap, model);
					javaEditorServices.parseFile(foundFile, classDiagramElementVisitor);
					//MyConnection connection = new MyConnection(null, "Implements", mainNode, classDiagramElementVisitor.getMainNode());
					//model.addConnection(connection);
					mainNode.getConnectedTo().add(classDiagramElementVisitor.getMainNode());
				}
				else{
					MyConnection connection = new MyConnection(null, "Implements", mainNode, interfaceNode);
					model.addConnection(connection);
				}
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

	
	public void findClassFile(File file, String name){
		String findName = name.replace(".", "/");
		findName = "/" + findName + ".java";
		File[] fileList = file.listFiles();
		for (File f : fileList) {
			if(f.isFile()){
				if(f.getAbsolutePath().contains(findName)){ 
					foundFile = f;
					}
			}
			else{
				if(f.isDirectory()){
					findClassFile(f, name);
				}
			}
		}
	}
	
	
	
	
}
