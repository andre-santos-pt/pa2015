package pa.iscde.checkstyle.view;

import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;
import pa.iscde.checkstyle.model.ViolationModelProvider;
import pt.iscte.pidesco.extensibility.PidescoView;

public class CheckStyleView implements PidescoView {

	private static final String[] COLUMN_NAMES_DETAIL_REPORT = { "Severity", "Resource", "Location", "Line", "Description" };

	private static final int[] COLUMN_WIDTHS_DETAIL_REPORT = { 100, 200, 300, 100, 200 };

	private static final String[] COLUMN_NAMES = { "Severity", "Violation Type", "Count", "Description", "" };

	private static final int[] COLUMN_WIDTHS = { 100, 300, 100, 300, 0 };

	private static CheckStyleView INSTANCE;

	private TableViewer viewer;

	private Table table;

	private Composite viewArea;

	private Map<String, Image> imageMap;

	private Button btMainReport;

	private Button btDetailReport;

	private Violation selectedViolation;
	
	private boolean isDetailReportOpened;

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
		if(isDetailReportOpened){
			return;
		}
		
		if (!viewArea.isDisposed()) {
			viewer.setInput(ViolationModelProvider.getInstance().getViolations());
		}
	}

	/**
	 * TODO
	 * 
	 * @param
	 */
	private void updateDetailReportModel() {
		if (!viewArea.isDisposed()) {
			viewer.setInput(selectedViolation.getDetails());
		}
	}

	/**
	 * TODO
	 * 
	 * @param viewArea
	 * @return
	 */
	private void render() {
		isDetailReportOpened = false;
		addNavigationButtons();
		addNavigationButtonsListeners();

		// SWT.SINGLE - Only one row is selected in the table
		// This is useful when we want to analyze the violation for a specific
		// violation type.
		viewer = new TableViewer(viewArea, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		addViewerListener();

		createColumns();

		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		addTableListener();

		viewer.setContentProvider(new ArrayContentProvider());
		updateModel();

		final GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;

		viewer.getControl().setLayoutData(gridData);
	}

	private void addNavigationButtonsListeners() {
		btMainReport.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isDetailReportOpened = false;
				updateModel();
				btDetailReport.setEnabled(true);
				btMainReport.setEnabled(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing to do.
			}
		});
		
		btDetailReport.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO
				System.out.println(e);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing to do.
			}
		});
	}

	/**
	 * TODO
	 */
	private void addNavigationButtons() {
		final GridLayout layout = new GridLayout(2, false);
		viewArea.setLayout(layout);

		btMainReport = new Button(viewArea, SWT.PUSH);
		btMainReport.setBounds(40, 40, 40, 40);
		btMainReport.setImage(imageMap.get("nav_main_report.png"));
		btMainReport.setEnabled(false);

		btDetailReport = new Button(viewArea, SWT.PUSH);
		btDetailReport.setBounds(40, 40, 40, 40);
		btDetailReport.setImage(imageMap.get("nav_detailed_report.png"));
		btDetailReport.setEnabled(false);
	}

	/**
	 * TODO
	 */
	private void updateColumns() {
		final TableColumn[] columns = viewer.getTable().getColumns();
		for (int i = 0; i < columns.length; ++i) {
			columns[i].setText(COLUMN_NAMES_DETAIL_REPORT[i]);
			columns[i].setWidth(COLUMN_WIDTHS_DETAIL_REPORT[i]);
		}
	}

	/**
	 * 
	 */
	private void addViewerListener() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent e) {
				updateColumns();
				updateDetailReportModel();
				btDetailReport.setEnabled(false);
				btMainReport.setEnabled(true);
				isDetailReportOpened = true;
			}
		});
	}

	/**
	 * This listener will be used to handle the selection event performed on the
	 * rows violation main view. Based on the selected row, we can retrieve the
	 * corresponding violation object and also the corresponding violation
	 * details.
	 * 
	 * The single selection
	 */
	private void addTableListener() {
		table.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedViolation = (Violation) e.item.getData();
				btDetailReport.setEnabled(true);
				btMainReport.setEnabled(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing to be done;
			}
		});
	}

	/**
	 * TODO
	 */
	private void createColumns() {
		TableViewerColumn col = createTableViewerColumn(COLUMN_NAMES[0], COLUMN_WIDTHS[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SeverityType severity = null;

				if (element instanceof Violation) {
					final Violation violation = (Violation) element;
					severity = violation.getSeverity();
				} else {
					final ViolationDetail violationDetail = (ViolationDetail) element;
					severity = violationDetail.getSeverity();
				}

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
				SeverityType severity = null;

				if (element instanceof Violation) {
					final Violation violation = (Violation) element;
					severity = violation.getSeverity();
				} else {
					final ViolationDetail violationDetail = (ViolationDetail) element;
					severity = violationDetail.getSeverity();
				}

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
				if (element instanceof Violation) {
					final Violation violation = (Violation) element;
					return violation.getType();
				}

				final ViolationDetail violationDetail = (ViolationDetail) element;
				return violationDetail.getResource();
			}
		});

		col = createTableViewerColumn(COLUMN_NAMES[2], COLUMN_WIDTHS[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Violation) {
					final Violation violation = (Violation) element;
					return String.valueOf(violation.getCount());
				}

				final ViolationDetail violationDetail = (ViolationDetail) element;
				return violationDetail.getLocation();
			}
		});

		col = createTableViewerColumn(COLUMN_NAMES[3], COLUMN_WIDTHS[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Violation) {
					final Violation violation = (Violation) element;
					return violation.getDescription();
				}

				final ViolationDetail violationDetail = (ViolationDetail) element;
				return String.valueOf(violationDetail.getLine() + 1);
			}
		});

		col = createTableViewerColumn(COLUMN_NAMES[4], COLUMN_WIDTHS[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof ViolationDetail) {
					final ViolationDetail violationDetail = (ViolationDetail) element;
					return violationDetail.getMessage();
				}

				return null;
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
