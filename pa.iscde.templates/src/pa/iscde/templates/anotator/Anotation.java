package pa.iscde.templates.anotator;

import java.util.ArrayList;
import java.util.Collection;

import pa.iscde.templates.model.ClassDeclaration;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;


/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */
public class Anotation {
	
	public class anotationKeyValue
	{
		public String key;
		public String value;
		
		public anotationKeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
	
	private SourceElement anotatorClass;
	private ClassDeclaration declaration;
	private Collection<anotationKeyValue> keyValues;
	
	

	/**
	 * Constructor das anotações recebe uma anotação como parametro.
	 * @param anotator
	 */
	public Anotation (SourceElement anotator)
	{
		setAnotatorClass(anotator);
		keyValues = new ArrayList<>();
	}
	
	/**
	 * @return Devolve o nome da Class da anotação.
	 */
	public String getSourceName()
	{
		return anotatorClass.getName().replace(".java", "");
	}
	
	
	/**
	 * @param anotator
	 */
	public void setAnotatorClass (SourceElement anotator)
	{
		this.anotatorClass = anotator;
		declaration = new ClassDeclaration(anotator);
	}
	
	/**
	 * Este é utilizado apenas no Refresh da tree.
	 * @return Devolve o nome do Pacote necessário da anotação.
	 */
	public String getRequiredPackage() {
		return this.declaration.packageID;
	}
	
	/**
	 * Adiciona uma nova key e o valor de uma anotação a nossa colecção de keys.
	 * @param key
	 * @param Value
	 */
	public void addKeyValue(String key, String Value) {
		this.keyValues.add(new anotationKeyValue(key, Value));
	}
	
	/**
	 * @return Devolve as linhas da anotação.
	 */
	public Collection<String> getAnnotationsLines ()
	{
		Collection<String> tmp = new ArrayList<>();
		if (keyValues.size() > 0)
		{
			tmp.add("@"+anotatorClass.getName().replace(".java","")+"(");
			int i = 0;
			for (anotationKeyValue a :keyValues)
			{
				if (i == keyValues.size()-1 && keyValues.size() != 1) tmp.add("\t"+a.key+" = \""+a.value+"\",");
				else tmp.add("\t"+a.key+" = \""+a.value+"\"");
			}
			tmp.add(")");
		}
		return tmp;
	}
}
