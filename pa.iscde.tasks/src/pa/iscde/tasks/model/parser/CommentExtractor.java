package pa.iscde.tasks.model.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.LineComment;

import pa.iscde.tasks.model.Task;
import pa.iscde.tasks.model.TaskOccurence;

/**
 * The AST doen't explicit return the comments. So, this class gathers the
 * comment information information replicate them in a String List.
 *
 */
public class CommentExtractor {

	private final String srcText;
	private final String fileName;
	private final String absFileName;

	public CommentExtractor(String srcText, String fileName, String absFileName) {
		this.srcText = srcText;
		this.fileName = fileName;
		this.absFileName = absFileName;
	}

	public List<TaskOccurence> getCommentDetails() {
		final List<TaskOccurence> occList = new ArrayList<>();
		final ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(srcText.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		for (Comment cmt : (List<Comment>) cu.getCommentList()) {
			cmt.accept(new CommentVisitor(occList, cu));
		}

		return occList;
	}

	/**
	 * This visitor extracts the comments from LINE and BLOCK comments. The
	 * JavaDoc comments are ignored.
	 * 
	 * @author ejpmateus
	 *
	 */
	private class CommentVisitor extends ASTVisitor {

		private final List<TaskOccurence> occList;
		private final CompilationUnit unit;

		public CommentVisitor(List<TaskOccurence> occList, CompilationUnit unit) {
			this.occList = occList;
			this.unit = unit;
		}

		public boolean visit(LineComment node) {
			return extractComment(node);
		}

		public boolean visit(BlockComment node) {
			return extractComment(node);
		}

		private boolean extractComment(Comment c) {
			final int start = c.getStartPosition();
			final int end = start + c.getLength();
			final TaskExtractor extractor = new TaskExtractor(srcText.substring(start, end));
			extractor.process();

			final List<Task> tasks = extractor.getTaskList();
			final List<Integer> relativePositions = extractor.getTaksRelativePostion();

			for (int i = 0; i < tasks.size(); i++) {
				int linePos = unit.getLineNumber(unit.getExtendedStartPosition(c) + relativePositions.get(i));
				occList.add(new TaskOccurence(tasks.get(i), fileName, absFileName, linePos));

			}

			return true;
		}

	}

}
