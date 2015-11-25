package pa.iscde.checkstyle.internal.tool;

import pa.iscde.checkstyle.internal.check.CheckStyleManager;
import pa.iscde.checkstyle.model.ViolationModelProvider;
import pa.iscde.checkstyle.view.CheckStyleView;
import pt.iscte.pidesco.extensibility.PidescoTool;

/**
 * This class implements the tool that is enabled in the tool bar of the project
 * browser component.
 */
public class CheckStyleTool implements PidescoTool {

	@Override
	public void run(boolean activate) {
		// Reset the existing calculated violations on the model used by views.
		ViolationModelProvider.getInstance().resetViolations();

		// Process the check style on the selected class elements, existing in
		// the shared model (@see SharedModel).
		CheckStyleManager.getInstance().process();

		// Update model used by views based on the violation recalculation
		// performed previously.
		ViolationModelProvider.getInstance().updateViolations(CheckStyleManager.getInstance().getViolations());

		// Refresh the view model.
		if (CheckStyleView.getInstance() != null) {
			CheckStyleView.getInstance().refreshModel();
		}
	}
}
