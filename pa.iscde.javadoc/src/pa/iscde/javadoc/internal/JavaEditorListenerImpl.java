package pa.iscde.javadoc.internal;

import java.io.File;

import org.osgi.service.log.LogService;

public class JavaEditorListenerImpl implements pt.iscte.pidesco.javaeditor.service.JavaEditorListener {

	private final LogService logService;

	public JavaEditorListenerImpl(LogService logService) {
		this.logService = logService;
	}

	@Override
	public void fileOpened(final File file) {
		logService.log(LogService.LOG_DEBUG, ">fileOpened(" + file + ")");
	}

	@Override
	public void fileSaved(final File file) {
		logService.log(LogService.LOG_DEBUG, ">fileSaved(" + file + ")");
	}

	@Override
	public void fileClosed(final File file) {
		logService.log(LogService.LOG_DEBUG, ">fileClosed(" + file + ")");
		JavaDocView.closeView();
	}

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		System.out.println(text);
		logService.log(LogService.LOG_DEBUG,
				">selectionChanged(" + file + "," + text + "," + offset + "," + length + ")");
	}
}