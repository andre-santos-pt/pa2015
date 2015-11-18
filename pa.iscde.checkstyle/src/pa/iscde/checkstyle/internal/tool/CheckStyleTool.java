package pa.iscde.checkstyle.internal.tool;

import pa.iscde.checkstyle.internal.CheckStyleServiceImpl;
import pa.iscde.checkstyle.view.CheckStyleView;
import pt.iscte.pidesco.extensibility.PidescoTool;

public class CheckStyleTool implements PidescoTool {

	@Override
	public void run(boolean activate) {
		final CheckStyleServiceImpl service = new CheckStyleServiceImpl();
		service.process();

		if (CheckStyleView.getInstance() != null) {
			CheckStyleView.getInstance().updateModel();
		}
	}
}
