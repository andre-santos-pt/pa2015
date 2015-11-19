package pt.iscte.pidesco.guibuilder.internal;

import pt.iscte.pidesco.guibuilder.ui.FigureMoverResizer;

public class ComponentInComposite {

	private String id;
	private Object object;
	private FigureMoverResizer fmr;
	
	public ComponentInComposite(String id, Object object, FigureMoverResizer fmr) {
		this.id = id;
		this.object = object;
		this.fmr = fmr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public FigureMoverResizer getFmr() {
		return fmr;
	}

	public void setFmr(FigureMoverResizer fmr) {
		this.fmr = fmr;
	}
	

}
