package pa.iscde.templates.anotator;
import java.util.Collection;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */

public interface Ianotate {
	public Collection<String> getAnotations ();
	public void addAnotation( Anotation anotation );
	public Collection<String> getIncludes();
}
