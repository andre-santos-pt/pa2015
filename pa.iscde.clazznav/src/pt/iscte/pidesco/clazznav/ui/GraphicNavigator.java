package pt.iscte.pidesco.clazznav.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.internal.ZoomManager;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * 
 * @author tiagocms
 *
 */
public class GraphicNavigator extends AbstractNavigator implements NavigatorInterface{


	private static GraphicNavigator instance;

	private boolean enabled = false;

	private static Graph graph;
	private static ArrayList<GraphNode> nodes = new ArrayList<>(); //Trocar por uma collection do guava

	private int style = SWT.NONE;

	private GraphicNavigator(Composite composite){
		super(composite);
		graph = new Graph(composite, style);
	}

	public static  GraphicNavigator getInstance(Composite composite) {
		if( instance == null)
			return instance = new GraphicNavigator(composite);
		return instance;
	} 

	public static  GraphicNavigator getInstance() {
		return instance;
	} 


	@Override
	public void build() {
		//		graph.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);
		//		graph.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING), true);

		final ZoomManager zoomManager = new ZoomManager(graph.getRootLayer(), graph.getViewport());
		graph.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseScrolled(MouseEvent e) {

				// TODO Auto-generated method stub
				if (e.count > 0 && zoomManager.getZoom() <= 2.0){
					zoomManager.setZoom(zoomManager.getZoom() * 1.5);

				}else if (zoomManager.getZoom() >= 0.75) {
					zoomManager.setZoom(zoomManager.getZoom() * 0.5);
				}
			}
		});

		graph.setVisible(enabled);
	}

	/**
	 * 
	 */
	public void enable(){
		setEnabled(true);
		graph.setVisible(true);
		getComposite().redraw(); //need it??
	}

	/**
	 * 
	 */
	public void disable(){
		setEnabled(false);
		graph.setVisible(false);
	}

	/**
	 * 
	 */
	public void refresh(){

		CustomNode source = null;

		if( ! graph.getNodes().isEmpty()){

			if ( graph.getNodes().get(graph.getNodes().size() - 1 )  instanceof CustomNode )
				source = (CustomNode) graph.getNodes().get(graph.getNodes().size() -1 );
		}

		CustomNode dest = new CustomNode( graph , files.get(files.size() - 1) );
		nodes.add( dest );

		if ( nodes.size() >= 2) {
			GraphConnection gc = new GraphConnection(graph, ZestStyles.CONNECTIONS_DIRECTED, source, dest );
			gc.setText(Integer.toString(nodes.indexOf(dest)));
			
		}

		graph.redraw();
		graph.update();
	}

	/**
	 * 
	 * @return
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void dispose() {
		graph.dispose();
	}
}
