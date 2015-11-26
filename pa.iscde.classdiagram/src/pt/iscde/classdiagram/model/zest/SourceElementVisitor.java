package pt.iscde.classdiagram.model.zest;

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
import pt.iscde.classdiagram.model.MyTopLevelElement;
import pt.iscde.classdiagram.model.types.EModifierType;
import pt.iscde.classdiagram.model.types.ETopElementType;

public class SourceElementVisitor extends ASTVisitor {

	private MyTopLevelElement topLevelNode;
	private Map<String, Image> imageMap; 
	public SourceElementVisitor(Map<String, Image> imageMap) {
		this.imageMap = imageMap;
	}
	
	public MyTopLevelElement getTopLevelNode() {
		return topLevelNode;
	}

	public boolean visit(EnumDeclaration node) {
		topLevelNode = new MyTopLevelElement(node.getName().getFullyQualifiedName(), node.getName().getIdentifier(), ETopElementType.ENUM, imageMap);
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		topLevelNode = new MyTopLevelElement(node.getName().getFullyQualifiedName() , node.getName().getIdentifier(), node.isInterface() ? ETopElementType.INTERFACE : ETopElementType.SUPERCLASS, imageMap);

		ITypeBinding typeBind = node.resolveBinding();
		ITypeBinding superTypeBind = typeBind.getSuperclass();
		ITypeBinding[] interfaceBinds = typeBind.getInterfaces();

		if (superTypeBind != null && !(superTypeBind.getName().equals("Object"))) {
			MyTopLevelElement superNode = new MyTopLevelElement(superTypeBind.getQualifiedName(), superTypeBind.getName(), ETopElementType.SUPERCLASS, imageMap);
			topLevelNode.getConnectedTo().add(superNode);
		}

		if (interfaceBinds != null) {
			for (ITypeBinding interfaceBind : interfaceBinds) {
				MyTopLevelElement interfaceNode = new MyTopLevelElement(interfaceBind.getQualifiedName(), interfaceBind.getName(), ETopElementType.INTERFACE, imageMap);
				topLevelNode.getConnectedTo().add(interfaceNode);
			}
		}

		// modifiers
		node.accept(new ASTVisitor() {
			@Override
			public boolean visit(Modifier node) {
				topLevelNode.addModifier(node);
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

		topLevelNode.addMethod(method);
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

		topLevelNode.addAttribute(attribute);
		return super.visit(node);
	}

}
