package pt.iscte.pidesco.guibuilder.internal;

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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.guibuilder.ui.FigureMoverResizer;

public class GuiBuilderView implements PidescoView {
	private Composite topComposite;
	private Composite bottomComposite;

	public GuiBuilderView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(final Composite viewArea, Map<String, Image> imageMap) {
		createViewTemporarySolution(viewArea, imageMap);
		populateTopComposite(topComposite);
		populateBottomComposite(bottomComposite);

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

		// Create the tree and some tree items
		// final Tree tree = new Tree(viewArea, SWT.NONE);
		// TreeItem item1 = new TreeItem(tree, SWT.NONE);
		// item1.setText("Item 1");
		// TreeItem item2 = new TreeItem(tree, SWT.NONE);
		// item2.setText("Item 2");
		// TreeItem item3 = new TreeItem(tree, SWT.NONE);
		// item3.setText("Item 3");
		// TreeItem item4 = new TreeItem(tree, SWT.NONE);
		// item4.setText("Item 4");
		//
		// // Create the drag source on the tree
		// DragSource ds = new DragSource(tree, DND.DROP_MOVE);
		// ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		// ds.addDragListener(new DragSourceAdapter() {
		// public void dragSetData(DragSourceEvent event) {
		// // Set the data to be the first selected item's text
		// event.data = tree.getSelection()[0].getText();
		// }
		// });
		//
		// // Create the button
		// final Button button = new Button(viewArea, SWT.FLAT);
		// button.setText("Button");
		// button.setAlignment(SWT.CENTER);
		//
		// // Create the drop target on the button
		// DropTarget dt = new DropTarget(button, DND.DROP_MOVE);
		// dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		// dt.addDropListener(new DropTargetAdapter() {
		// public void drop(DropTargetEvent event) {
		// // Set the buttons text to be the text being dropped
		// button.setText((String) event.data);
		// }
		// });

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
		new FigureMoverResizer(fig);

		RoundedRectangle fig2 = new RoundedRectangle();
		fig2.setBounds(new org.eclipse.draw2d.geometry.Rectangle(50, 50, 300, 200));
		fig2.setCornerDimensions(new Dimension(20, 20));
		new FigureMoverResizer(fig2);

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

	private void populateTopComposite(Composite composite) {
		Canvas canvas = new Canvas(composite, SWT.NONE);
		LightweightSystem lws = new LightweightSystem(canvas);
		Figure contents = new Figure();

		XYLayout contentsLayout = new XYLayout();

		contents.setLayoutManager(contentsLayout);

		RectangleFigure fig = new RectangleFigure();
		fig.setBackgroundColor(canvas.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		fig.setBounds(new org.eclipse.draw2d.geometry.Rectangle(5, 5, 100, 200));
		new FigureMoverResizer(fig);

		RoundedRectangle fig2 = new RoundedRectangle();
		fig2.setBounds(new org.eclipse.draw2d.geometry.Rectangle(50, 50, 300, 200));
		fig2.setCornerDimensions(new Dimension(20, 20));
		new FigureMoverResizer(fig2);

		lws.setContents(contents);

		contents.add(fig);
		contents.add(fig2);

	}

	private void populateBottomComposite(Composite composite) {

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

				for (int t = 0; t <= 4; t++) {
					Button button = new Button(compositeButtons, SWT.PUSH);
					button.setAlignment(SWT.CENTER);

					switch (t) {
					case 0:
						button.setText("JButton");
						break;
					case 1:
						button.setText("JLabel");
						break;
					case 2:
						button.setText("JTextField");
						break;
					case 3:
						button.setText("JRadioButton");
						break;
					case 4:
						button.setText("JCheckBox");
						break;

					default:
						break;
					}
				}
				compositeButtons.setSize(500, 90);
				break;

			case 1:
				tabItem.setText("Layouts");

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
}
