package pa.iscde.javadoc.internal;

import java.io.File;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.osgi.service.log.LogService;

public class JavaEditorListenerImpl implements pt.iscte.pidesco.javaeditor.service.JavaEditorListener {

	private final LogService logService;

	public JavaEditorListenerImpl(LogService logService) {
		this.logService = logService;
	}

	@Override
	public void fileOpened(final File file) {
		JavadDocActivator.getInstance().getJavaEditorServices().parseFile(file, new ASTVisitor() {
			@Override
			public boolean visit(MethodDeclaration node) {
				System.out.println("Method: " + node.getName());
				System.out.println("JavaDoc: " + node.getJavadoc() + "\n");
				return false;
			}
		});
		
		logService.log(LogService.LOG_DEBUG, ">fileOpened(" + file + ")");
	}

	@Override
	public void fileSaved(final File file) {
		logService.log(LogService.LOG_DEBUG, ">fileSaved(" + file + ")");
	}

	@Override
	public void fileClosed(final File file) {
		logService.log(LogService.LOG_DEBUG, ">fileClosed(" + file + ")");
	}

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		System.out.println(text);
		logService.log(LogService.LOG_DEBUG,
				">selectionChanged(" + file + "," + text + "," + offset + "," + length + ")");
	}
}