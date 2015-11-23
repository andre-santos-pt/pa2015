package pt.iscde.classdiagram.internal;

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

public class ClassDiagramElementVisitor extends ASTVisitor {


	private MyTopLevelElement mainNode = null;
	private Map<String, Image> imageMap;
	private NodeModelContentProvider model;
	
	

	
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


}
