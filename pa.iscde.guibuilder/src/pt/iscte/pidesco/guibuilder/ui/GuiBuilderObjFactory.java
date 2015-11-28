package pt.iscte.pidesco.guibuilder.ui;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import pt.iscte.pidesco.guibuilder.internal.ComponentInComposite;

public class GuiBuilderObjFactory {
	/*
	 * GUIBuilder specific parameters
	 */
	// Dimensions
	private static final Point DEFAULT_FAKEWINDOW_INIT_DIM = new Point(400, 400);
	private static final Point DEFAULT_BUTTON_DIM = new Point(100, 90);
	private static final Point DEFAULT_LABEL_BACKGND_DIM = new Point(70, 30);
	private static final Point DEFAULT_LABEL_DIM = new Point(55, 15);
	private static final Point DEFAULT_TXTFIELD_BACKGND_DIM = new Point(150, 40);
	private static final Point DEFAULT_TXTFIELD_DIM = new Point(100, 20);
	private static final Point DEFAULT_RADIOBTN_BACKGND_DIM = new Point(150, 40);
	private static final Point DEFAULT_RADIOBTN_DIM = new Point(100, 20);
	private static final Point DEFAULT_CHCKBOX_BACKGND_DIM = new Point(150, 40);
	private static final Point DEFAULT_CHCKBOX_DIM = new Point(100, 20);

	// Default text
	private static final String DEFAULT_BTN_TXT = "New Button";
	private static final String DEFAULT_LABEL_TXT = "New Label";
	private static final String DEFAULT_TXTFIELD_TXT = "New Textfield";
	private static final String DEFAULT_RADIOBTN_TXT = "New choice";
	private static final String DEFAULT_CHCKBOX_TXT = "New checkbox";

	public static Figure createGuiBuilderCanvas(Canvas canvas) {
		RectangleFigure fig = new RectangleFigure();
		fig.setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_GRAY));
		fig.setBounds(new Rectangle(5, 5, DEFAULT_FAKEWINDOW_INIT_DIM.x, DEFAULT_FAKEWINDOW_INIT_DIM.y));
		new FigureMoverResizer(fig, null, "");
		return fig;
	}

	public static ComponentInComposite createComponentFamilyObject(String cmpName, Canvas canvas, Figure contents) {
		Point cursorLocation = Display.getCurrent().getCursorLocation();
		Point relativeCursorLocation = Display.getCurrent().getFocusControl().toControl(cursorLocation);

		GuiLabels.GUIBuilderComponent component = null;
		for (GuiLabels.GUIBuilderComponent c : GuiLabels.GUIBuilderComponent.values()) {
			if (c.str().equals(cmpName)) {
				component = c;
				break;
			}
		}

		System.out.println("# Adding " + cmpName + " to canvas");

		switch (component) {
		case BTN:
			RoundedRectangle button = new RoundedRectangle();
			button.setCornerDimensions(new Dimension(20, 20));

			if (relativeCursorLocation.x < DEFAULT_FAKEWINDOW_INIT_DIM.x
					&& relativeCursorLocation.x > (DEFAULT_BUTTON_DIM.x / 2)
					&& relativeCursorLocation.y < DEFAULT_FAKEWINDOW_INIT_DIM.y
					&& relativeCursorLocation.y > (DEFAULT_BUTTON_DIM.y / 2)) {
				button.setBounds(new Rectangle(relativeCursorLocation.x - (DEFAULT_BUTTON_DIM.x / 2),
						relativeCursorLocation.y - (DEFAULT_BUTTON_DIM.y / 2), DEFAULT_BUTTON_DIM.x,
						DEFAULT_BUTTON_DIM.y));
				System.out.println("entrou1");
			} else if (relativeCursorLocation.x < (DEFAULT_BUTTON_DIM.x / 2)
					&& relativeCursorLocation.y < (DEFAULT_BUTTON_DIM.y / 2)) {
				button.setBounds(new Rectangle(0, 0, DEFAULT_BUTTON_DIM.x, DEFAULT_BUTTON_DIM.y));
				System.out.println("entrou2");
			} else if (relativeCursorLocation.x < (DEFAULT_BUTTON_DIM.x / 2)
					&& relativeCursorLocation.x < DEFAULT_FAKEWINDOW_INIT_DIM.x
					&& relativeCursorLocation.y < DEFAULT_FAKEWINDOW_INIT_DIM.y) {
				button.setBounds(new Rectangle(0, relativeCursorLocation.y - (DEFAULT_BUTTON_DIM.y / 2),
						DEFAULT_BUTTON_DIM.x, DEFAULT_BUTTON_DIM.y));
				System.out.println("entrou3");

			} else if (relativeCursorLocation.y < (DEFAULT_BUTTON_DIM.y / 2)
					&& relativeCursorLocation.x < DEFAULT_FAKEWINDOW_INIT_DIM.x
					&& relativeCursorLocation.y < DEFAULT_FAKEWINDOW_INIT_DIM.y) {
				button.setBounds(new Rectangle(relativeCursorLocation.x - (DEFAULT_BUTTON_DIM.x / 2), 0,
						DEFAULT_BUTTON_DIM.x, DEFAULT_BUTTON_DIM.y));
				System.out.println("entrou4");
			}
			contents.add(button);
			FigureMoverResizer fmrButton = new FigureMoverResizer(button, canvas, DEFAULT_BTN_TXT);

			System.out.println("relativeCursorLocation: " + relativeCursorLocation.x + "," + relativeCursorLocation.y);
			return new ComponentInComposite(cmpName + "\t" + System.currentTimeMillis(), button, fmrButton);

		case LABEL:
			Label label = new Label(canvas, SWT.BORDER);
			label.setText(DEFAULT_LABEL_TXT);
			label.setSize(DEFAULT_LABEL_DIM);
			label.setLocation(relativeCursorLocation.x, relativeCursorLocation.y);

			RoundedRectangle backgroundLabel = new RoundedRectangle();
			backgroundLabel.setCornerDimensions(new Dimension(10, 10));
			backgroundLabel.setBounds(new Rectangle(relativeCursorLocation.x, relativeCursorLocation.y,
					DEFAULT_LABEL_BACKGND_DIM.x, DEFAULT_LABEL_BACKGND_DIM.y));
			contents.add(backgroundLabel);

			// FigureMoverResizer fmrLabel = new
			// FigureMoverResizer(backgroundLabel, canvas, label,
			// DEFAULT_LABEL_DIM.x,
			// DEFAULT_LABEL_DIM.y);

			return new ComponentInComposite(cmpName + "\t" + System.currentTimeMillis(), label, null);

		case TEXTFIELD:
			Text textField = new Text(canvas, SWT.BORDER);
			textField.setText(DEFAULT_TXTFIELD_TXT);
			textField.setSize(DEFAULT_TXTFIELD_DIM);
			textField.setLocation(relativeCursorLocation.x, relativeCursorLocation.y);

			RoundedRectangle backgroundTextField = new RoundedRectangle();
			backgroundTextField.setCornerDimensions(new Dimension(10, 10));
			backgroundTextField.setBounds(new Rectangle(relativeCursorLocation.x, relativeCursorLocation.y,
					DEFAULT_TXTFIELD_BACKGND_DIM.x, DEFAULT_TXTFIELD_BACKGND_DIM.y));
			contents.add(backgroundTextField);

			// FigureMoverResizer fmrTextField = new
			// FigureMoverResizer(backgroundTextField, canvas, textField,
			// DEFAULT_TXT_FIELD_DIM.x, DEFAULT_TXT_FIELD_DIM.y);

			return new ComponentInComposite(cmpName + "\t" + System.currentTimeMillis(), textField, null);

		// case RADIO_BTN:
		// Button radioButton = new Button(canvas, SWT.RADIO);
		// radioButton.setText(DEFAULT_RADIOBTN_TXT);
		// radioButton.setSize(DEFAULT_RADIOBTN_DIM);
		// radioButton.setLocation(relativeCursorLocation.x,
		// relativeCursorLocation.y);
		// radioButton.setSelection(true);
		//
		// RoundedRectangle backgroundRadioButton = new RoundedRectangle();
		// backgroundRadioButton.setCornerDimensions(new Dimension(10, 10));
		// backgroundRadioButton.setBounds(new
		// Rectangle(relativeCursorLocation.x, relativeCursorLocation.y,
		// DEFAULT_RADIOBTN_BACKGND_DIM.x, DEFAULT_RADIOBTN_BACKGND_DIM.y));
		// contents.add(backgroundRadioButton);
		//
		// // FigureMoverResizer fmrRadioButton = new
		// // FigureMoverResizer(backgroundRadioButton, canvas, radioButton,
		// // DEFAULT_RADIOBTN_DIM.x, DEFAULT_RADIOBTN_DIM.y);
		//
		// return new ComponentInComposite(cmpName + "\t" +
		// System.currentTimeMillis(), radioButton, null);

		case CHK_BOX:
			Button checkBox = new Button(canvas, SWT.CHECK);
			checkBox.setText(DEFAULT_CHCKBOX_TXT);
			checkBox.setLocation(relativeCursorLocation.x, relativeCursorLocation.y);
			checkBox.setSize(DEFAULT_CHCKBOX_DIM);

			RoundedRectangle backgroundCheckBox = new RoundedRectangle();
			backgroundCheckBox.setCornerDimensions(new Dimension(10, 10));
			backgroundCheckBox.setBounds(new org.eclipse.draw2d.geometry.Rectangle(relativeCursorLocation.x,
					relativeCursorLocation.y, DEFAULT_CHCKBOX_BACKGND_DIM.x, DEFAULT_CHCKBOX_BACKGND_DIM.y));
			contents.add(backgroundCheckBox);

			// FigureMoverResizer fmrCheckBox = new
			// FigureMoverResizer(backgroundCheckBox, canvas, checkBox,
			// DEFAULT_CHCKBOX_DIM.x, DEFAULT_CHCKBOX_DIM.y);

			return new ComponentInComposite(cmpName + "\t" + System.currentTimeMillis(), checkBox, null);

		default:
			throw new IllegalAccessError("Switch case not defined!");
		}
	}

	public static ComponentInComposite createLayoutFamilyObject(String cmpName, Canvas canvas, Figure contents) {
		// TODO Define method
		System.err.println("Method undefined");
		return null;
	}

	public static ComponentInComposite createContainerFamilyObject(String cmpName, Canvas canvas, Figure contents) {
		// TODO Define method
		System.err.println("Method undefined");
		return null;
	}
}
