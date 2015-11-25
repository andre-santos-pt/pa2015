package pa.iscde.tasks.control.tools;

import pa.iscde.tasks.gui.view.TableView;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class TasksRefreshTool implements PidescoTool {

	@Override
	public void run(boolean activate) {
		TableView.getInstance().refresh();
	}

}
