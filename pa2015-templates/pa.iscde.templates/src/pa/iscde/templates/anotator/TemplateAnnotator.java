package pa.iscde.templates.anotator;

import java.util.ArrayList;
import java.util.Collection;

public class TemplateAnnotator implements Ianotate {

	private String defaultAnotation = "@Override";
	protected Collection<Anotation> annotations;
	
	public TemplateAnnotator() {
		annotations = new ArrayList<>();
	}
	
	@Override
	public Collection<String> getAnotations() {
		Collection<String> tmp = new ArrayList<>();
		tmp.add(defaultAnotation);
		for (Anotation a : annotations)
		{
			tmp.addAll(a.getAnnotationsLines());
		}
		return tmp;
	}

	@Override
	public void addAnotation( Anotation anotation )
	{
		annotations.add(anotation);
	}
	
	@Override
	public Collection<String> getIncludes()
	{
		Collection<String> tmp = new ArrayList<>();
		for (Anotation a : annotations)
		{
			if (!tmp.contains("import "+a.getRequiredPackage()+"."+a.getSourceName()+";"))
				tmp.add("import "+a.getRequiredPackage()+"."+a.getSourceName()+";");
		}
		return tmp;
	}
}
