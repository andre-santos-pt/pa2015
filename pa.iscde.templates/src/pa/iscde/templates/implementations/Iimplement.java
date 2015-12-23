package pa.iscde.templates.implementations;

import java.io.File;
import java.util.Collection;

import pa.iscde.templates.model.MethodDeclaration;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */


public interface Iimplement {
	public Collection<MethodDeclaration> implement(SourceElement target);
	public Collection<MethodDeclaration> implement(File target);
	public String getName();
	public SourceElement getSource();
	public Collection<SourceElement> getImplementations();
	public void addImplementation (SourceElement s);
}
