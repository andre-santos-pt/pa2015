package pa.iscde.templates.anotator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */

public class TemplateAnnotator implements Ianotate {

	private String defaultAnotation = "@Override";
	protected Collection<Anotation> annotations;
	
	/**
	 * Constructor do Template Annotator.
	 */
	public TemplateAnnotator() {
		annotations = new ArrayList<>();
	}
	
	/**
	 * @return Devolve uma colecção de anotações presentes.
	 */
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


	/**
	 * Adiciona uma nova anotação a nossa colecção de anotações.
	 * @param anotation
	 */
	@Override
	public void addAnotation( Anotation anotation )
	{
		annotations.add(anotation);
	}
	
	/**
	 * @return Devolve o pacote e a class a qual pertence cada anotação.
	 */
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
