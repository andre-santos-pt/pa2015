package pa.iscde.javadoc.generator;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class StringTemplateVisitor extends ASTVisitor {

	private static final STGroup group;

	private StringBuilder stringBuilder;

	static {
		group = new STGroupFile("/pa/iscde/javadoc/templates/javadoc.stg");
	}

	public StringTemplateVisitor(final StringBuilder stringBuilder) {
		super(true);
		this.stringBuilder = stringBuilder;
	}

	private void renderNode(final ASTNode node, final String operation) {
		final ST template = group.getInstanceOf(operation + "_" + node.getClass().getSimpleName());
		if (null != template) {
			template.add(node.getClass().getSimpleName(), node);
			stringBuilder.append(template.render());
		}
	}

	@Override
	public void preVisit(ASTNode node) {
		renderNode(node, "preVisit");
		super.preVisit(node);
	}

	@Override
	public void postVisit(ASTNode node) {
		renderNode(node, "postVisit");
		super.postVisit(node);
	}

}
