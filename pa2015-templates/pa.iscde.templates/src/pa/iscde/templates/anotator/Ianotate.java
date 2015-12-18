package pa.iscde.templates.anotator;
import java.util.Collection;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public interface Ianotate {
	public Collection<String> getAnotations ();
	public void addAnotation( Anotation anotation );
	public Collection<String> getIncludes();
}
