package pa.iscde.javadoc.internal;

import java.util.Collection;

import org.osgi.service.log.LogService;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class ProjectBrowserListenerImpl implements pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener {

	private final LogService logService;

	public ProjectBrowserListenerImpl(final LogService logService) {
		this.logService = logService;
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
