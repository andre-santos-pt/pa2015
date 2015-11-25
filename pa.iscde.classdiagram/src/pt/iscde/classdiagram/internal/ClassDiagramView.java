package pt.iscde.classdiagram.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
//import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import pt.iscde.classdiagram.model.zest.ClassDiagramContentProvider;
import pt.iscde.classdiagram.model.zest.ClassDiagramLabelProvider;
import pt.iscde.classdiagram.model.zest.NodeModelContentProvider;
import pt.iscde.classdiagram.service.ClassDiagramServices;
import pt.iscte.pidesco.extensibility.PidescoServices;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class ClassDiagramView implements PidescoView, ClassDiagramServices, ProjectBrowserListener, JavaEditorListener {
	private static final String VIEW_ID = "pt.iscte.pidesco.classdiagram";
	private static ClassDiagramView instance;
	private static PidescoServices pidescoServices;
	private static ProjectBrowserServices browserServices;
	private static JavaEditorServices javaEditorServices;

	private GraphViewer viewer;
	private NodeModelContentProvider model;
	private Map<String, Image> imageMap;

	public ClassDiagramView() {
		pidescoServices = ClassDiagramActivator.getInstance().getPidescoServices();
		browserServices = ClassDiagramActivator.getInstance().getBrowserServices();
		javaEditorServices = ClassDiagramActivator.getInstance().getJavaEditorServices();
		browserServices.addListener(this);
		javaEditorServices.addListener(this);
	}

	public static ClassDiagramView getInstance() {
		if (instance == null)
			pidescoServices.openView(VIEW_ID);

		return instance;
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		this.imageMap = imageMap;
		viewer = new GraphViewer(viewArea, SWT.BORDER);
		viewer.getGraphControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setContentProvider(new ClassDiagramContentProvider());
		viewer.setLabelProvider(new ClassDiagramLabelProvider());
		model = new NodeModelContentProvider();
		viewer.setInput(model.getNodes());
		LayoutAlgorithm layout = setLayout();
		viewer.setLayoutAlgorithm(layout, true);
		viewer.applyLayout();
	}

	private LayoutAlgorithm setLayout() {
		TreeLayoutAlgorithm springLayoutAlgorithm = new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		HorizontalShift horizontalShift = new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		return new CompositeLayoutAlgorithm(new LayoutAlgorithm[] { horizontalShift, springLayoutAlgorithm });
	}

	@Override
	public void update(SourceElement sourceElement) {
		actualizaDiagrama(sourceElement.getFile());
	}

	List<String> metodos = new ArrayList<String>();

	@Override
	public void doubleClick(SourceElement element) {
		actualizaDiagrama(element.getFile());
	}

	private void actualizaDiagrama(File file) {
		model = new NodeModelContentProvider();
		ClassDiagramElementVisitor elementVisitor = new ClassDiagramElementVisitor(imageMap, model);
		javaEditorServices.parseFile(file, elementVisitor);
		viewer.setInput(model.getNodes());
	}

	

	@Override
	public void selectionChanged(Collection<SourceElement> selection) {
		if (selection != null && !selection.isEmpty()) {
			for (SourceElement sourceElement : selection) {
				if (sourceElement.isClass()) {
					update(sourceElement);
					break;
				}
			}
		}

	}

	@Override
	public void fileOpened(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fileSaved(File file) {
		actualizaDiagrama(file);

	}

	@Override
	public void fileClosed(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(File file, String text, int offset, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public IFigure getClassImage(SourceElement sourceElement) {
		// TODO Auto-generated method stub
		return null;
	}

}
