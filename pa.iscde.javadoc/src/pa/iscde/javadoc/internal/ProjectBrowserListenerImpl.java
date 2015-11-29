package pa.iscde.javadoc.internal;

import java.util.Collection;

import org.osgi.service.log.LogService;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class ProjectBrowserListenerImpl implements pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener {

	private final LogService logService;

	public ProjectBrowserListenerImpl() {
		this.logService = JavaDocServiceLocator.getLogService();
	}

	@Override
	public void doubleClick(SourceElement element) {
		logService.log(LogService.LOG_DEBUG, ">doubleClick(" + element + ")");
	}

	@Override
	public void selectionChanged(Collection<SourceElement> selection) {
		logService.log(LogService.LOG_DEBUG, ">selectionChanged(" + selection + ")");
	}

}
