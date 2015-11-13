package pa.iscde.checkstyle.view;

import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationModelProvider;
import pt.iscte.pidesco.extensibility.PidescoView;

public class CheckStyleView implements PidescoView {

	private static final String[] COLUMN_NAMES = { "Severity", "Violation Type", "Count", "Description" };

	private static final int[] COLUMN_WIDTHS = { 100, 300, 100, 300 };

	private static CheckStyleView INSTANCE;

	private TableViewer viewer;

	private Table table;

	private Composite viewArea;

	private Map<String, Image> imageMap;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		INSTANCE = this;
		this.viewArea = viewArea;
		this.imageMap = imageMap;
		render();
	}

	/**
	 * TODO
	 * 
	 * @return
	 */
	public static CheckStyleView getInstance() {
		return INSTANCE;
	}

	/**
	 * TODO
	 */
	public void updateModel() {
		if (!viewArea.isDisposed()) {
			viewer.setInput(ViolationModelProvider.getInstance().getViolations());
		}
	}

	/**
	 * TODO
	 * 
	 * @param viewArea
	 * @return
	 */
	private void render() {
		viewer = new TableViewer(viewArea, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns();

		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());

		final GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;

		viewer.getControl().setLayoutData(gridData);
	}

	// create the columns for the table
	private void createColumns() {
		TableViewerColumn col = createTableViewerColumn(COLUMN_NAMES[0], COLUMN_WIDTHS[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final Violation violation = (Violation) element;
				SeverityType severity = violation.getSeverity();

				String text = null;
				switch (severity) {
				case BLOCKED:
					text = severity.getName();
					break;
				case CRITICAL:
					text = severity.getName();
					break;
				case WARNING:
					text = severity.getName();
					break;
				}
				return text;
			}

			@Override
			public Image getImage(Object element) {
				final Violation violation = (Violation) element;
				SeverityType severity = violation.getSeverity();

				Image image = null;
				switch (severity) {
				case BLOCKED:
				case CRITICAL:
					image = imageMap.get(severity.getImage());
					break;
				case WARNING:
					image = imageMap.get(severity.getImage());
					break;
				}
				return image;
			}
		});

		col = createTableViewerColumn(COLUMN_NAMES[1], COLUMN_WIDTHS[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final Violation violation = (Violation) element;
				return violation.getType();
			}
		});

		col = createTableViewerColumn(COLUMN_NAMES[2], COLUMN_WIDTHS[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final Violation violation = (Violation) element;
				return String.valueOf(violation.getCount());
			}
		});

		col = createTableViewerColumn(COLUMN_NAMES[3], COLUMN_WIDTHS[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				final Violation violation = (Violation) element;
				return violation.getDescription();
			}
		});
	}

	/**
	 * TODO
	 * 
	 * @param title
	 * @param width
	 * @param colNumber
	 * @return
	 */
	private TableViewerColumn createTableViewerColumn(String title, int width, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(width);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
}
