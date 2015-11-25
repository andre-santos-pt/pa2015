package pt.iscte.pidesco.guibuilder.ui;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.guibuilder.internal.ComponentInComposite;

public class GuiBuilderView implements PidescoView {
	/*
	 * Parameterization (measures in pixels)
	 */
	private final Point BOTTOM_COMPOSITE_BUTTONS_DIM = new Point(150, 90);
	private final int BOTTOM_COMPOSITE_MINIMUM_HEIGHT = BOTTOM_COMPOSITE_BUTTONS_DIM.y + 78;

	/*
	 * Variables
	 */
	private Composite topComposite;
	private Composite bottomComposite;
	private ArrayList<ComponentInComposite> components = new ArrayList<ComponentInComposite>();

	/*
	 * Constructors and main methods
	 */
	public GuiBuilderView() {

	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		createBaseFrame(viewArea, imageMap);
		populateTopComposite(topComposite, imageMap);
		populateBottomComposite(bottomComposite, imageMap);
	}

	// TODO Change to include status bar
	private void createBaseFrame(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new FillLayout());

		// Create a LiveSashForm with VERTICAL and with a minimum height
		LiveSashForm sashForm = new LiveSashForm(viewArea, SWT.VERTICAL);

		topComposite = new Composite(sashForm, SWT.BORDER);
		topComposite.setLayout(new FillLayout());

		bottomComposite = new Composite(sashForm, SWT.BORDER);
		bottomComposite.setLayout(new FillLayout());

		// Define the relation between both composites
		sashForm.setWeights(new int[] { 6, 2 });
		sashForm.dragMinimum = BOTTOM_COMPOSITE_MINIMUM_HEIGHT;
	}

	/*
	 * Top composite methods
	 */
	private void populateTopComposite(final Composite composite, final Map<String, Image> imageMap) {
		final Canvas canvas = new Canvas(composite, SWT.NONE);
		LightweightSystem lws = new LightweightSystem(canvas);
		final Figure contents = new Figure();

		XYLayout contentsLayout = new XYLayout();
		contents.setLayoutManager(contentsLayout);
		lws.setContents(contents);

		contents.add(GuiBuilderObjFactory.createGuiBuilderCanvas(canvas));

		// Create the drop target on the composite
		DropTarget dt = new DropTarget(composite, DND.DROP_MOVE);
		dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		dt.addDropListener(new DropTargetAdapter() {
			public void drop(DropTargetEvent event) {
				String[] data = event.data.toString().split("\t");

				if (data.length != 2) {
					throw new IllegalArgumentException("Invalid reference for draggable object");
				} else {
					int index = Integer.parseInt(data[0]);
					GuiLabels.GUIBuilderObjectFamily of = GuiLabels.GUIBuilderObjectFamily.values()[index];
					String objectName = data[1];

					switch (of) {
					case COMPONENTS:
						components.add(GuiBuilderObjFactory.createComponentFamilyObject(objectName, canvas, contents));
						break;
					case LAYOUTS:
						components.add(GuiBuilderObjFactory.createLayoutFamilyObject(objectName, canvas, contents));
						break;
					case CONTAINERS:
						components.add(GuiBuilderObjFactory.createContainerFamilyObject(objectName, canvas, contents));
						break;
					default:
						throw new IllegalAccessError("Switch case not defined!");
					}
				}
			}
		});
		mouseEventTopComposite(canvas);
	}

	// TODO Refactoring
	private void mouseEventTopComposite(final Canvas canvas) {
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent event) {
				if (event.button == 3) { // Right click
					for (ComponentInComposite componentInComposite : components) {
						boolean found = false;

						for (GuiLabels.GUIBuilderComponent c : GuiLabels.GUIBuilderComponent.values()) {
							if (componentInComposite.getId().contains(c.str())) {
								found = true;
								
								switch(c){
								case BTN:
									RoundedRectangle jButton = ((RoundedRectangle) componentInComposite.getObject());

									if (event.x > jButton.getLocation().x
											&& event.x < jButton.getLocation().x + jButton.getBounds().width
											&& event.y > jButton.getLocation().y
											&& event.y < jButton.getLocation().y + jButton.getBounds().height) {
										System.out.println("Right click !");

										openDialogMenu(canvas, componentInComposite.getFmr(), event.x, event.y);
									}
									break;
								case LABEL:
									
									break;
								case TEXTFIELD:
									
									break;
								
//								case RADIO_BTN:
//									
//									break;
									
								case CHK_BOX:
									
									break;
									
								}
							}
						}

						if (!found) {
							for (GuiLabels.GUIBuilderLayout l : GuiLabels.GUIBuilderLayout.values()) {
								if (componentInComposite.getId().contains(l.str())) {
									found = true;

									// TODO Define method
									System.err.println("Undefined");
								}
							}
						}

						if (!found) {
							for (GuiLabels.GUIBuilderContainer c : GuiLabels.GUIBuilderContainer.values()) {
								if (componentInComposite.getId().contains(c.str())) {
									found = true;

									// TODO Define method
									System.err.println("Undefined");
								}
							}
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
				// TODO Define method
				System.out.println("Double click");
			}
		});

	}

	private void openDialogMenu(Canvas canvas, FigureMoverResizer fmr, int x, int y) {
		Menu popupMenu = new Menu(canvas);
		MenuItem renameItem = new MenuItem(popupMenu, SWT.NONE);
		renameItem.setText("Rename");

		// Menu COLOR
		MenuItem colorItem = new MenuItem(popupMenu, SWT.CASCADE);
		colorItem.setText("Choose Background Color");
		Menu chooseColorItemMenu = new Menu(colorItem);
		colorItem.setMenu(chooseColorItemMenu);

		for (GuiLabels.Color c : GuiLabels.Color.values()) {
			MenuItem item = new MenuItem(chooseColorItemMenu, SWT.NONE);
			item.setText(c.name());
			addDialogMenuListener(item, canvas, fmr, x, y);
		}

		popupMenu.setVisible(true);
	}

	private void addDialogMenuListener(final MenuItem item, final Canvas canvas, final FigureMoverResizer fmr,
			final int x, final int y) {
		item.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				String itemText = item.getText();

				if (itemText.equals(GuiLabels.DialogMenuLabel.RENAME.str())) {
					String inputText = new InputDialog(x, y, topComposite.getShell(), SWT.BAR).open();
					fmr.setText(inputText);
				} else {
					for (GuiLabels.Color c : GuiLabels.Color.values()) {
						if (c.name().equals(itemText)) {
							if (itemText.equals(GuiLabels.Color.Other.name())) {
								ColorDialog dlg = new ColorDialog(canvas.getShell());
								dlg.setRGB(fmr.getFigure().getBackgroundColor().getRGB());

								// Change the title bar text
								dlg.setText("Choose a Color");
								fmr.getFigure().setBackgroundColor(new Color(canvas.getDisplay(), dlg.open()));
							} else {
								fmr.getFigure().setBackgroundColor(canvas.getDisplay().getSystemColor(c.swt_value()));
							}
							break;
						}
					}
				}
			}
		});
	}

	/*
	 * Bottom composite methods
	 */
	private void populateBottomComposite(Composite composite, Map<String, Image> imageMap) {
		TabFolder tabFolder = new TabFolder(composite, SWT.TOP);

		for (GuiLabels.GUIBuilderObjectFamily tabLabel : GuiLabels.GUIBuilderObjectFamily.values()) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NULL);

			ScrolledComposite sci = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL);
			Composite compositeButtons = new Composite(sci, SWT.NONE);
			compositeButtons.setLayout(new FillLayout());

			sci.setContent(compositeButtons);
			tabItem.setControl(sci);
			tabItem.setText(tabLabel.str());
			tabItem.setImage(imageMap.get("icon_tab_components.png"));

			switch (tabLabel) {
			case COMPONENTS:
				for (GuiLabels.GUIBuilderComponent c : GuiLabels.GUIBuilderComponent.values()) {
					Button button = new Button(compositeButtons, SWT.CENTER | SWT.WRAP | SWT.PUSH);
					button.setAlignment(SWT.CENTER);
					button.setText(c.str());
					addDragListener(button, tabLabel.ordinal());
				}
				compositeButtons.setSize(BOTTOM_COMPOSITE_BUTTONS_DIM.x * GuiLabels.GUIBuilderComponent.values().length,
						BOTTOM_COMPOSITE_BUTTONS_DIM.y);
				break;

			case LAYOUTS:
				for (GuiLabels.GUIBuilderLayout c : GuiLabels.GUIBuilderLayout.values()) {
					Button button = new Button(compositeButtons, SWT.CENTER | SWT.WRAP | SWT.PUSH);
					button.setAlignment(SWT.CENTER);
					button.setText(c.str());
					addDragListener(button, tabLabel.ordinal());
				}
				compositeButtons.setSize(BOTTOM_COMPOSITE_BUTTONS_DIM.x * GuiLabels.GUIBuilderLayout.values().length,
						BOTTOM_COMPOSITE_BUTTONS_DIM.y);
				break;

			case CONTAINERS:
				for (GuiLabels.GUIBuilderContainer c : GuiLabels.GUIBuilderContainer.values()) {
					Button button = new Button(compositeButtons, SWT.CENTER | SWT.WRAP | SWT.PUSH);
					button.setAlignment(SWT.CENTER);
					button.setText(c.str());
					addDragListener(button, tabLabel.ordinal());
				}
				compositeButtons.setSize(BOTTOM_COMPOSITE_BUTTONS_DIM.x * GuiLabels.GUIBuilderContainer.values().length,
						BOTTOM_COMPOSITE_BUTTONS_DIM.y);
				break;

			default:
				throw new IllegalAccessError("Switch case not defined!");
			}
		}
	}

	private void addDragListener(final Button button, final int objectTypeOrdinal) {
		DragSource ds = new DragSource(button, DND.DROP_MOVE);
		ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });

		ds.addDragListener(new DragSourceAdapter() {
			public void dragSetData(DragSourceEvent event) {
				event.data = objectTypeOrdinal + "\t" + button.getText();
			}
		});
	}

	/*
	 * Que métodos são estes!?
	 */
	private void addPopUpMenu(final Label label) {
		// escolher as opcoes para ediÃ§Ã£o//ver o window builder
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

	private Image getCheckedImage(Display display) {
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
}
