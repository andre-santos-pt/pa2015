package pt.iscde.classdiagram.service;

import org.eclipse.draw2d.IFigure;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public interface ClassDiagramServices {
	
	public void update(SourceElement sourceElement);
	
	public IFigure getClassImage(SourceElement sourceElement);
}
