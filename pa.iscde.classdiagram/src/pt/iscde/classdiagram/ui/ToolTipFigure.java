package pt.iscde.classdiagram.ui;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

public class ToolTipFigure extends FlowPage {
	private final Border TOOLTIP_BORDER = new MarginBorder(0, 2, 1, 0);
	private TextFlow message;

	public ToolTipFigure(String tooltipText) {
		setOpaque(true);
		setBackgroundColor(ColorConstants.lightBlue);
		setForegroundColor(ColorConstants.white);
		setFont(new Font(null, "Courier", 12, SWT.NORMAL));
		setBorder(TOOLTIP_BORDER);
		message = new TextFlow();
		message.setText(tooltipText);
		add(message);
	}

	@Override
	public Dimension getPreferredSize(int w, int h) {
		Dimension d = super.getPreferredSize(-1, -1);
		if (d.width > 150)
			d = super.getPreferredSize(150, -1);
		return d;
	}

}