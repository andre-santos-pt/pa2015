package pa.iscde.javadoc.generator;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

public class StringTemplateVisitor extends ASTVisitor {

	private StringBuilder sb = new StringBuilder();

	public StringTemplateVisitor() {
		super(true);
	}

	private void renderNode(ASTNode node) {
		STGroup group = new STGroupDir("/pa/iscde/javadoc/templates");				
		ST template = group.getInstanceOf(node.getClass().getSimpleName() );
		if (null != template) {
			template.add(node.getClass().getSimpleName(), node);
		}
		sb.append(template.render());
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		renderNode(node);
		return super.visit(node);
	}

	public StringBuilder getSb() {
		return sb;
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}
	
	

}
