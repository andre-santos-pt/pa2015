package pt.iscte.pidesco.guibuilder.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.guibuilder.ui.FigureMoverResizer;

public class GuiBuilderView implements PidescoView {
	private Composite topComposite;
	private Composite bottomComposite;
	private HashMap<String, Object> componentsInTopComposite = new HashMap<String, Object>();
	private HashMap<String, FigureMoverResizer> figureMoverResizerComponentsInTopComposite = new HashMap<String, FigureMoverResizer>();

	public GuiBuilderView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {

		createViewTemporarySolution(viewArea, imageMap);
		populateTopComposite(topComposite, imageMap);
		populateBottomComposite(bottomComposite, imageMap);

		// viewArea.setLayout(new RowLayout());
		//
		// final Label label = new Label(viewArea, SWT.NONE);
		// label.setText("text on the label");
		//
		// //label.setImage(getCheckedImage(label.getDisplay()));
		// label.setImage(imageMap.get("frame.png"));
		//
		// addTabbar(viewArea);
		//
		// addPopUpMenu(label);
		//
		//
		// addFigDragDrop(viewArea);

	}

	private void addFigDragDrop(Composite viewArea) {

		Canvas canvas = new Canvas(viewArea, SWT.NONE);
		LightweightSystem lws = new LightweightSystem(canvas);
		Figure contents = new Figure();

		XYLayout contentsLayout = new XYLayout();

		contents.setLayoutManager(contentsLayout);

		RectangleFigure fig = new RectangleFigure();
		fig.setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		fig.setBounds(new org.eclipse.draw2d.geometry.Rectangle(5, 5, 100, 200));
		new FigureMoverResizer(fig, null, "");

		RoundedRectangle fig2 = new RoundedRectangle();
		fig2.setBounds(new org.eclipse.draw2d.geometry.Rectangle(50, 50, 300, 200));
		fig2.setCornerDimensions(new Dimension(20, 20));
		new FigureMoverResizer(fig2, null, "");

		lws.setContents(contents);

		contents.add(fig);
		contents.add(fig2);

	}

	private void addTabbar(final Composite viewArea) {
		final TabFolder tabFolder = new TabFolder(viewArea, SWT.BORDER);

		for (int loopIndex = 0; loopIndex < 10; loopIndex++) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NULL);
			tabItem.setText("Tab " + loopIndex);

			// Text text = new Text(tabFolder, SWT.BORDER);
			// text.setText("This is page " + loopIndex);
			// tabItem.setControl(text);
			final Button button = new Button(tabFolder, SWT.FLAT);
			button.setText("Button");
			button.setAlignment(SWT.CENTER);
			// button.setImage(new Image(viewArea.getDisplay(), "frame.png"));
			tabItem.setControl(button);

		}
		tabFolder.setSize(400, 10);
	}

	static Image getCheckedImage(Display display) {
		// Image image = new Image(display, 500, 500);
		Image image = new Image(display, "images/frame.png");

		// System.out.println(System.getProperty("user.dir"));
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
		gc.fillOval(0, 0, 16, 16);
		gc.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
		gc.drawLine(0, 0, 16, 16);
		gc.drawLine(16, 0, 0, 16);
		gc.dispose();
		return image;
	}

	private void addPopUpMenu(final Label label) {
		// escolher as opcoes para edição//ver o window builder
		Menu popupMenu = new Menu(label);
		MenuItem newItem = new MenuItem(popupMenu, SWT.CASCADE);
		newItem.setText("New");
		MenuItem refreshItem = new MenuItem(popupMenu, SWT.NONE);
		refreshItem.setText("Refresh");
		MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
		deleteItem.setText("Delete");

		Menu newMenu = new Menu(popupMenu);
		newItem.setMenu(newMenu);

		MenuItem shortcutItem = new MenuItem(newMenu, SWT.NONE);
		shortcutItem.setText("Shortcut");
		MenuItem iconItem = new MenuItem(newMenu, SWT.NONE);
		iconItem.setText("Icon");

		label.setMenu(popupMenu);
	}

	private void createViewTemporarySolution(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new FillLayout());

		// Create the SashForm with VERTICAL
		SashForm sashForm = new SashForm(viewArea, SWT.VERTICAL);

		topComposite = new Composite(sashForm, SWT.BORDER);
		topComposite.setLayout(new FillLayout());

		bottomComposite = new Composite(sashForm, SWT.BORDER);
		bottomComposite.setLayout(new FillLayout());

		// Define the relation between both composites
		sashForm.setWeights(new int[] { 4, 1 });
	}

	private void populateTopComposite(final Composite composite, final Map<String, Image> imageMap) {

		final Canvas canvas = new Canvas(composite, SWT.NONE);
		LightweightSystem lws = new LightweightSystem(canvas);
		final Figure contents = new Figure();

		// por alterar consoante o tamanho da "janela"
		final int fakeWindowWidth = 400;
		final int fakeWindowHeight = 400;

		XYLayout contentsLayout = new XYLayout();

		contents.setLayoutManager(contentsLayout);

		RectangleFigure fig = new RectangleFigure();
		fig.setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_GRAY));
		fig.setBounds(new org.eclipse.draw2d.geometry.Rectangle(5, 5, fakeWindowWidth, fakeWindowHeight));
		new FigureMoverResizer(fig, null, "");

		lws.setContents(contents);

		contents.add(fig);

		// Create the drop target on the composite
		DropTarget dt = new DropTarget(composite, DND.DROP_MOVE);
		dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter() {
			public void drop(DropTargetEvent event) {

				switch (event.data.toString()) {
				case "JButton":

					final Point cursorLocation = Display.getCurrent().getCursorLocation();
					final Point relativeCursorLocation = Display.getCurrent().getFocusControl()
							.toControl(cursorLocation);

					final int buttonWidth = 100;
					final int buttonHeight = 90;

					final RoundedRectangle jButton = new RoundedRectangle();

					jButton.setCornerDimensions(new Dimension(20, 20));
					final FigureMoverResizer fmr = new FigureMoverResizer(jButton, canvas, "New Button");

					contents.add(jButton);

					if (relativeCursorLocation.x < fakeWindowWidth && relativeCursorLocation.x > (buttonWidth / 2)
							&& relativeCursorLocation.y < fakeWindowHeight
							&& relativeCursorLocation.y > (buttonHeight / 2)) {
						jButton.setBounds(
								new org.eclipse.draw2d.geometry.Rectangle(relativeCursorLocation.x - (buttonWidth / 2),
										relativeCursorLocation.y - (buttonHeight / 2), buttonWidth, buttonHeight));
						System.out.println("entrou1");
					} else if (relativeCursorLocation.x < (buttonWidth / 2)
							&& relativeCursorLocation.y < (buttonHeight / 2)) {
						jButton.setBounds(new org.eclipse.draw2d.geometry.Rectangle(0, 0, buttonWidth, buttonHeight));
						System.out.println("entrou2");
					} else if (relativeCursorLocation.x < (buttonWidth / 2)
							&& relativeCursorLocation.x < fakeWindowWidth
							&& relativeCursorLocation.y < fakeWindowHeight) {
						jButton.setBounds(new org.eclipse.draw2d.geometry.Rectangle(0,
								relativeCursorLocation.y - (buttonHeight / 2), buttonWidth, buttonHeight));
						System.out.println("entrou3");

					} else if (relativeCursorLocation.y < (buttonHeight / 2)
							&& relativeCursorLocation.x < fakeWindowWidth
							&& relativeCursorLocation.y < fakeWindowHeight) {
						jButton.setBounds(new org.eclipse.draw2d.geometry.Rectangle(
								relativeCursorLocation.x - (buttonWidth / 2), 0, buttonWidth, buttonHeight));
						System.out.println("entrou4");
					}

					System.out.println(
							"relativeCursorLocation: " + relativeCursorLocation.x + "," + relativeCursorLocation.y);

					componentsInTopComposite.put("JButton", jButton);
					figureMoverResizerComponentsInTopComposite.put("JButton", fmr);

					break;
				case "JLabel":
					final Point cursorLocation2 = Display.getCurrent().getCursorLocation();
					final Point relativeCursorLocation2 = Display.getCurrent().getFocusControl()
							.toControl(cursorLocation2);

					final String text = "new label";

					final Label label = new Label(canvas, SWT.BORDER);
					label.setText(text);
					label.setLocation(relativeCursorLocation2.x, relativeCursorLocation2.y);
					label.setSize(70, 20);

					// if (composite != null) {
					// composite.addPaintListener(new PaintListener() {
					//
					// @Override
					// public void paintControl(PaintEvent e) {
					//
					// e.gc.drawText(text, 100 - (text.length() * 3), 100);
					// composite.redraw();
					// composite.update();
					// }
					// });
					// }

					break;

				case "JTextField":
					final Point cursorLocation3 = Display.getCurrent().getCursorLocation();
					final Point relativeCursorLocation3 = Display.getCurrent().getFocusControl()
							.toControl(cursorLocation3);

					Text textField = new Text(canvas, SWT.BORDER);
					textField.setSize(100, 20);
					textField.setText("new textField");
					textField.setLocation(relativeCursorLocation3.x, relativeCursorLocation3.y);

					break;

				case "JRadioButton":

					final Point cursorLocation4 = Display.getCurrent().getCursorLocation();
					final Point relativeCursorLocation4 = Display.getCurrent().getFocusControl()
							.toControl(cursorLocation4);

					Button radioButton = new Button(canvas, SWT.RADIO);
					radioButton.setSelection(true);
					radioButton.setText("Choice 1");
					radioButton.setLocation(relativeCursorLocation4.x, relativeCursorLocation4.y);
					radioButton.setSize(100, 20);

					break;

				case "JCheckBox":
					final Point cursorLocation5 = Display.getCurrent().getCursorLocation();
					final Point relativeCursorLocation5 = Display.getCurrent().getFocusControl()
							.toControl(cursorLocation5);

					Button checkBox = new Button(canvas, SWT.CHECK);
					checkBox.setText("Checkbox 1");
					checkBox.setLocation(relativeCursorLocation5.x, relativeCursorLocation5.y);
					checkBox.setSize(100, 20);

					break;
				default:
					break;
				}

			}
		});
		mouseEventTopComposite(canvas);

	}

	private void populateBottomComposite(final Composite composite, final Map<String, Image> imageMap) {

		final TabFolder tabFolder = new TabFolder(composite, SWT.TOP);

		for (int loopIndex = 0; loopIndex < 3; loopIndex++) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NULL);

			final ScrolledComposite sci = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL);
			Composite compositeButtons = new Composite(sci, SWT.NONE);
			compositeButtons.setLayout(new FillLayout());

			sci.setContent(compositeButtons);

			tabItem.setControl(sci);

			switch (loopIndex) {
			case 0:
				tabItem.setText("Components");
				tabItem.setImage(imageMap.get("icon_tab_components.png"));

				for (int t = 0; t <= 4; t++) {
					final Button button = new Button(compositeButtons, SWT.PUSH);
					button.setAlignment(SWT.CENTER);

					switch (t) {
					case 0:
						button.setText("JButton");
						addDragListener(button);

						break;
					case 1:
						button.setText("JLabel");
						addDragListener(button);

						break;
					case 2:
						button.setText("JTextField");
						addDragListener(button);

						break;
					case 3:
						button.setText("JRadioButton");
						addDragListener(button);

						break;
					case 4:
						button.setText("JCheckBox");
						addDragListener(button);

						break;

					default:
						break;
					}
				}
				compositeButtons.setSize(500, 90);
				break;

			case 1:
				tabItem.setText("Layouts");
				tabItem.setImage(imageMap.get("icon_tab_layouts.png"));

				for (int t = 0; t < 3; t++) {
					Button button = new Button(compositeButtons, SWT.PUSH);
					button.setAlignment(SWT.CENTER);

					switch (t) {
					case 0:
						button.setText("Flow \n Layout");
						break;
					case 1:
						button.setText("Absolute \n Layout");
						break;
					case 2:
						button.setText("Border \n Layout");
						break;

					default:
						break;
					}
				}
				compositeButtons.setSize(300, 90);
				break;

			case 2:
				tabItem.setText("Containers");
				tabItem.setImage(imageMap.get("icon_tab_containers.png"));

				Button button = new Button(compositeButtons, SWT.PUSH);
				button.setAlignment(SWT.CENTER);
				button.setText("JPanel");
				compositeButtons.setSize(100, 90);
				break;

			default:
				break;
			}
		}
	}

	private void addDragListener(final Button button) {

		DragSource ds = new DragSource(button, DND.DROP_MOVE);
		ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });

		ds.addDragListener(new DragSourceAdapter() {
			public void dragSetData(DragSourceEvent event) {
				event.data = button.getText();
			}
		});

	}

	private void mouseEventTopComposite(final Canvas canvas) {

		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent event) {
				if (event.button == 3) { // Right click
					if (componentsInTopComposite.containsKey("JButton")) {
						RoundedRectangle jButton = ((RoundedRectangle) componentsInTopComposite.get("JButton"));
						if (event.x > jButton.getLocation().x
								&& event.x < jButton.getLocation().x + jButton.getBounds().width
								&& event.y > jButton.getLocation().y
								&& event.y < jButton.getLocation().y + jButton.getBounds().height) {
							System.out.println("Right click !");

							if (figureMoverResizerComponentsInTopComposite.containsKey("JButton"))
								openMenuDialog(canvas, figureMoverResizerComponentsInTopComposite.get("JButton"),
										event.x, event.y);

						}

					}
				}

			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				System.out.println("Double click");
				// fazer aqui o double click para depois entrar no codigo count

			}
		});

	}

	private void openMenuDialog(final Canvas canvas, final FigureMoverResizer fmr, final int x, final int y) {

		final Menu popupMenu = new Menu(canvas);

		MenuItem renameItem = new MenuItem(popupMenu, SWT.NONE);
		renameItem.setText("Rename");

		// Menu COLOR
		MenuItem colorItem = new MenuItem(popupMenu, SWT.CASCADE);
		colorItem.setText("Choose Background Color");
		Menu chooseColorItemMenu = new Menu(colorItem);
		colorItem.setMenu(chooseColorItemMenu);

		MenuItem colorBlack = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorBlack.setText("Black");
		MenuItem colorWhite = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorWhite.setText("White");
		MenuItem colorYellow = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorYellow.setText("Yellow");
		MenuItem colorBlue = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorBlue.setText("Blue");
		MenuItem colorGray = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorGray.setText("Gray");
		MenuItem colorGreen = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorGreen.setText("Green");
		MenuItem colorRed = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorRed.setText("Red");

		MenuItem colorOther = new MenuItem(chooseColorItemMenu, SWT.NONE);
		colorOther.setText("Other..");

		popupMenu.setVisible(true);

		addSelectionListener(renameItem, canvas, fmr, x, y);
		addSelectionListener(colorBlack, canvas, fmr, x, y);
		addSelectionListener(colorWhite, canvas, fmr, x, y);
		addSelectionListener(colorYellow, canvas, fmr, x, y);
		addSelectionListener(colorBlue, canvas, fmr, x, y);
		addSelectionListener(colorGray, canvas, fmr, x, y);
		addSelectionListener(colorGreen, canvas, fmr, x, y);
		addSelectionListener(colorRed, canvas, fmr, x, y);
		addSelectionListener(colorOther, canvas, fmr, x, y);

	}

	private void addSelectionListener(final MenuItem item, final Canvas canvas, final FigureMoverResizer fmr,
			final int x, final int y) {
		item.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				switch (item.getText()) {

				case "Rename":
					String inputText = new InputDialog(x, y, topComposite.getShell(), SWT.BAR).open();
					fmr.setText(inputText);
					break;
				case "Black":
					fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_BLACK));
					// MessageBox messageBox = new MessageBox(canvas.getShell(),
					// SWT.ICON_INFORMATION);
					// messageBox.setMessage("Color changed!");
					// messageBox.open();
					break;
				case "White":
					fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_WHITE));
					break;
				case "Yellow":
					fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
					break;
				case "Blue":
					fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_BLUE));
					break;
				case "Gray":
					fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_GRAY));
					break;
				case "Green":
					fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_GREEN));
					break;
				case "Red":
					fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_RED));
					break;
				case "Other..":
					ColorDialog dlg = new ColorDialog(canvas.getShell());

					dlg.setRGB(fmr.getFigure().getBackgroundColor().getRGB());

					// Change the title bar text
					dlg.setText("Choose a Color");

					// Open the dialog and retrieve the selected color
					RGB rgb = dlg.open();

					fmr.getFigure().setBackgroundColor(new Color(canvas.getDisplay(), rgb));
					break;

				default:
					break;
				}

			}
		});
	}
}
