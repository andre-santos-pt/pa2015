package pt.iscte.pidesco.windowBuilder.ui;


import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.UpdateManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class FigureMoverResizer implements MouseListener, MouseMotionListener {
		private static final int CORNER = 10;

		private enum Handle {
			TOP_LEFT {
				@Override
				Rectangle getNewPosition(Rectangle bounds, Dimension offset) {
					return new Rectangle(
							bounds.x+offset.width, 
							bounds.y+offset.height, 
							bounds.width-offset.width, 
							bounds.height-offset.height);
				}

				@Override
				boolean match(int x, int y, Rectangle bounds) {
					return x < CORNER && y < CORNER;
				}
			}, 
			TOP_RIGHT {
				@Override
				Rectangle getNewPosition(Rectangle bounds, Dimension offset) {
					return new Rectangle(
							bounds.x, 
							bounds.y+offset.height, 
							bounds.width+offset.width, 
							bounds.height-offset.height);
				}

				@Override
				boolean match(int x, int y, Rectangle bounds) {
					return x > bounds.width-CORNER && y < CORNER;
				}
			},
			BOT_LEFT {
				@Override
				Rectangle getNewPosition(Rectangle bounds, Dimension offset) {
					return new Rectangle(
							bounds.x+offset.width, 
							bounds.y, 
							bounds.width-offset.width, 
							bounds.height+offset.height);
				}

				@Override
				boolean match(int x, int y, Rectangle bounds) {
					return x < CORNER && y > bounds.height-CORNER;
				}
			},
			BOT_RIGHT {
				@Override
				Rectangle getNewPosition(Rectangle bounds, Dimension offset) {
					return new Rectangle(
							bounds.x, 
							bounds.y, 
							bounds.width+offset.width, 
							bounds.height+offset.height);
				}

				@Override
				boolean match(int x, int y, Rectangle bounds) {
					return x > bounds.width-CORNER && y > bounds.height-CORNER;
				}
			};

			abstract Rectangle getNewPosition(Rectangle bounds, Dimension offset);
			abstract boolean match(int x, int y, Rectangle bounds);
			
			static Handle getHandle(int x, int y, Rectangle bounds) {
				for(Handle h : values())
					if(h.match(x, y, bounds))
						return h;
				
				return null;
			}
		}

		private final IFigure figure;
		private Point location;
		private Handle handle;

		public FigureMoverResizer(IFigure figure) {
			if(figure == null)
				throw new NullPointerException();
			
			this.figure = figure;
			figure.addMouseListener(this);
			figure.addMouseMotionListener(this);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			Dimension d = event.getLocation().getDifference(figure.getBounds().getLocation());
			handle = Handle.getHandle(d.width, d.height, figure.getBounds());
			location = event.getLocation();
			event.consume();
		}


		@Override
		public void mouseDragged(MouseEvent event) {
			if (location == null)
				return;

			Point newLocation = event.getLocation();
			if (newLocation == null)
				return;

			Dimension offset = newLocation.getDifference(location);
			if (offset.width == 0 && offset.height == 0)
				return;

			location = newLocation;

			UpdateManager updateMgr = figure.getUpdateManager();
			LayoutManager layoutMgr = figure.getParent().getLayoutManager();
			Rectangle bounds = figure.getBounds();
			updateMgr.addDirtyRegion(figure.getParent(), bounds);

			if(handle != null) { // resize
				Rectangle newPos = handle.getNewPosition(bounds, offset);
				layoutMgr.setConstraint(figure, newPos);
				updateMgr.addDirtyRegion(figure.getParent(), newPos);
				layoutMgr.layout(figure.getParent());
			}
			else { // move
				bounds = bounds.getCopy().translate(offset.width, offset.height);
				layoutMgr.setConstraint(figure, bounds);
				figure.translate(offset.width, offset.height);
				updateMgr.addDirtyRegion(figure.getParent(), bounds);
			}
			event.consume();
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			if (location == null)
				return;
			location = null;
			handle = null;
			event.consume();
		}

		@Override
		public void mouseEntered(MouseEvent me) { }

		@Override
		public void mouseExited(MouseEvent me) { }

		@Override
		public void mouseHover(MouseEvent me) { }

		@Override
		public void mouseMoved(MouseEvent me) { }

		@Override
		public void mouseDoubleClicked(MouseEvent me) { }
	}