package pa.iscde.checkstyle.view;

import java.io.File;
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

import pa.iscde.checkstyle.internal.CheckStyleActivator;
import pa.iscde.checkstyle.model.SeverityType;
import pa.iscde.checkstyle.model.Violation;
import pa.iscde.checkstyle.model.ViolationDetail;
import pa.iscde.checkstyle.model.ViolationModelProvider;
import pt.iscte.pidesco.extensibility.PidescoView;

/**
 * This class is responsible to render the main and detailed reports associated
 * to Checkstyle analysis performed in the classes existing in the projects.
 * 
 * It is responsible too to handle the events performed in the main and detailed
 * reports.
 */
public class CheckStyleView implements PidescoView {

	/**
	 * The columns' names for detailed report.
	 */
	private static final String[] COLUMN_NAMES_DETAILED_REPORT = { "Severity", "Resource", "Location", "Line", "Description" };

	/**
	 * The columns' widths for detailed report.
	 */
	private static final int[] COLUMN_WIDTHS_DETAILED_REPORT = { 100, 200, 300, 100, 200 };

	/**
	 * The columns' names for main report.
	 */
	private static final String[] COLUMN_NAMES_MAIN_REPORT = { "Severity", "Violation Type", "Count", "Description", "" };

	/**
	 * The columns' widths for main report.
	 */
	private static final int[] COLUMN_WIDTHS_MAIN_REPORT = { 100, 300, 100, 300, 0 };

	/**
	 * Singleton instance for this class loaded eagerly.
	 */
	private static CheckStyleView INSTANCE;

	/**
	 * The table viewer.
	 */
	private TableViewer viewer;

	/**
	 * The table used to present the main and detailed report.
	 */
	private Table table;

	/**
	 * The view are in which the main and detailed reports are rendered.
	 */
	private Composite viewArea;

	/**
	 * The auxiliary structures with mapping for all image resources existing in
	 * the application.
	 */
	private Map<String, Image> imageMap;

	/**
	 * The navigation button used to navigate from detailed report to main
	 * report.
	 */
	private Button btMainReport;

	/**
	 * The navigation button used to navigate from main report to detailed
	 * report.
	 */
	private Button btDetailReport;
	
	/**
	 * The delete button used to clean the violations existing in the data model.
	 */
	private Button btDeleteViolations;

	/**
	 * Holds the violation object associated to the row selected in the main
	 * report.
	 */
	private Violation selectedViolation;

	/**
	 * Holds the violation detail object associated to the row selected in the
	 * detailed report.
	 */
	private ViolationDetail selectedViolationDetail;

	/**
	 * Used to validate if the detailed report is being used.
	 */
	private boolean isDetailedReportOpened;

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		INSTANCE = this;
		this.viewArea = viewArea;
		this.imageMap = imageMap;
		render();
	}

	/**
	 * This method is used to return the singleton instance of this class.
	 * 
	 * @return The singleton instance.
	 */
	public static CheckStyleView getInstance() {
		return INSTANCE;
	}

	/**
	 * This method is used to refresh the data model used by main report. If the
	 * detailed report is being used, the data model for main report will not be
	 * updated.
	 */
	public void refreshModel() {
		if (isDetailedReportOpened) {
			return;
		}

		if (!viewArea.isDisposed()) {
			viewer.setInput(ViolationModelProvider.getInstance().getViolations());
		}
	}

	/**
	 * This method is used to refresh the data model used by detailed report.
	 */
	private void refreshDetailedReportModel() {
		if (!viewArea.isDisposed()) {
			viewer.setInput(selectedViolation.getDetails());
		}
	}

	/**
	 * This method is responsible to create the skeleton of the views for main
	 * and detailed reports and add the needed listeners in order to handle the
	 * actions performed on them.
	 */
	private void render() {
		isDetailedReportOpened = false;

		addButtons();
		addButtonsListeners();

		viewer = new TableViewer(viewArea, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		addViewerListener();

		createColumns();

		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		addTableListener();

		viewer.setContentProvider(new ArrayContentProvider());
		refreshModel();

		viewer.getControl().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 3, 0));
	}

	/**
	 * This method is used to create the navigation buttons used to navigate
	 * between the main report to detailed report and vice-versa.
	 * And also the violations delete button.
	 */
	private void addButtons() {
		final GridLayout layout = new GridLayout(3, false);
		viewArea.setLayout(layout);

		btMainReport = new Button(viewArea, SWT.PUSH);
		btMainReport.setBounds(40, 40, 40, 40);
		btMainReport.setImage(imageMap.get("nav_main_report.png"));
		btMainReport.setEnabled(false);
		btMainReport.setToolTipText("Go back to the master view");

		btDetailReport = new Button(viewArea, SWT.PUSH);
		btDetailReport.setBounds(40, 40, 40, 40);
		btDetailReport.setImage(imageMap.get("nav_detailed_report.png"));
		btDetailReport.setEnabled(false);
		btDetailReport.setToolTipText("List all the violations of this type");
		
		btDeleteViolations = new Button(viewArea, SWT.PUSH);
		btDeleteViolations.setBounds(40, 40, 40, 40);
		btDeleteViolations.setImage(imageMap.get("delete_violations.png"));
		btDeleteViolations.setEnabled(false);
		btDeleteViolations.setToolTipText("Clear Checkstyle violations");
	}

	/**
	 * This method is used to add the listeners to handle the actions performed
	 * on the navigation buttons and delete button
	 */
	private void addButtonsListeners() {
		btMainReport.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isDetailedReportOpened = false;
				refreshModel();
				btDetailReport.setEnabled(selectedViolation != null);
				btMainReport.setEnabled(false);
				updateColumns(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing to do.
			}
		});

		btDetailReport.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isDetailedReportOpened = true;
				refreshDetailedReportModel();
				btDetailReport.setEnabled(false);
				btMainReport.setEnabled(true);
				selectedViolation = null;
				updateColumns(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing to do.
			}
		});
	}

	/**
	 * This method is used to update the columns' names and widths between the
	 * visualization between the main report and the detailed report.
	 */
	private void updateColumns(boolean isMainReport) {
		final TableColumn[] columns = viewer.getTable().getColumns();

		if (!isMainReport) {
			for (int i = 0; i < columns.length; ++i) {
				columns[i].setText(COLUMN_NAMES_DETAILED_REPORT[i]);
				columns[i].setWidth(COLUMN_WIDTHS_DETAILED_REPORT[i]);
			}
		} else {
			for (int i = 0; i < columns.length; ++i) {
				columns[i].setText(COLUMN_NAMES_MAIN_REPORT[i]);
				columns[i].setWidth(COLUMN_WIDTHS_MAIN_REPORT[i]);
			}
		}
	}

	/**
	 * This method is used to add a listener to handle the double click event on
	 * the rows on the table associated to main and detailed reports.
	 * 
	 * If the selected row belongs to main report, the double click event will
	 * open the detail report to present the detail of violations for linked to
	 * the violation type associated to the selected row.
	 * 
	 * If the selected row belongs belongs to detailed report, the double click
	 * event will open the file in which the violation was detected, selecting
	 * also the text associated to the mentioned violation.
	 */
	private void addViewerListener() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent e) {
				if (!isDetailedReportOpened) {
					updateColumns(false);
					refreshDetailedReportModel();
					btDetailReport.setEnabled(false);
					btMainReport.setEnabled(true);
					isDetailedReportOpened = true;
					selectedViolation = null;
				} else {
					CheckStyleActivator.getInstance().getJavaEditorServices().selectText(
							new File(selectedViolationDetail.getLocation()), selectedViolationDetail.getOffset(), 0);
				}
			}
		});
	}

	/**
	 * This listener will be used to handle the selection event performed on the
	 * rows violation main and detail view. Based on the selected row, we can
	 * retrieve the corresponding violation object and also the corresponding
	 * violation details and vice-versa.
	 */
	private void addTableListener() {
		table.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.item.getData() instanceof Violation) {
					selectedViolation = (Violation) e.item.getData();
					btDetailReport.setEnabled(true);
					btMainReport.setEnabled(false);
				}

				if (e.item.getData() instanceof ViolationDetail) {
					selectedViolationDetail = (ViolationDetail) e.item.getData();
					btDetailReport.setEnabled(false);
					btMainReport.setEnabled(true);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing to be done;
			}
		});
	}

	/**
	 * This method is used to create the columns for the main report and add the
	 * corresponding label providers, in order to populate the table data to be
	 * present, either for main and detailed report.
	 */
	private void createColumns() {
		TableViewerColumn col = createTableViewerColumn(COLUMN_NAMES_MAIN_REPORT[0], COLUMN_WIDTHS_MAIN_REPORT[0], 0);
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

		col = createTableViewerColumn(COLUMN_NAMES_MAIN_REPORT[1], COLUMN_WIDTHS_MAIN_REPORT[1], 1);
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

		col = createTableViewerColumn(COLUMN_NAMES_MAIN_REPORT[2], COLUMN_WIDTHS_MAIN_REPORT[2], 2);
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

		col = createTableViewerColumn(COLUMN_NAMES_MAIN_REPORT[3], COLUMN_WIDTHS_MAIN_REPORT[3], 3);
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

		col = createTableViewerColumn(COLUMN_NAMES_MAIN_REPORT[4], COLUMN_WIDTHS_MAIN_REPORT[4], 4);
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
	 * This method is used to create a particular table column to be added to a
	 * table.
	 * 
	 * @param title
	 *            The column title.
	 * @param width
	 *            The column width.
	 * @param colNumber
	 *            The column number.
	 * @return An object representing a column.
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
