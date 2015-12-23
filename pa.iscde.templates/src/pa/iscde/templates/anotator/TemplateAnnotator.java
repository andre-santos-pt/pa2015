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
	 * @return Devolve uma colec��o de anota��es presentes.
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
	 * Adiciona uma nova anota��o a nossa colec��o de anota��es.
	 * @param anotation
	 */
	@Override
	public void addAnotation( Anotation anotation )
	{
		annotations.add(anotation);
	}
	
	/**
	 * @return Devolve o pacote e a class a qual pertence cada anota��o.
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
