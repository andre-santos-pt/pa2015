package pa.iscde.tasks.gui.view;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import pa.iscde.tasks.model.Task;
import pa.iscde.tasks.model.Task.PRIORITY;

/**
 * Implements a table filter for Priority
 */
public class PriorityFilter extends ViewerFilter {

	private final PRIORITY filterPriority;

	public PriorityFilter(final PRIORITY filterPriority) {
		this.filterPriority = filterPriority;
	}

	@Override
	public boolean select(Viewer viewer, Object parent, Object element) {
		return ((Task) element).getPriority() == filterPriority;
	}
	
}

