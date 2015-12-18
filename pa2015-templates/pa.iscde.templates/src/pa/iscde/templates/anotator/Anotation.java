package pa.iscde.templates.anotator;

import java.util.ArrayList;
import java.util.Collection;

import pa.iscde.templates.model.ClassDeclaration;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

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
	
	
	public Anotation (SourceElement anotator)
	{
		setAnotatorClass(anotator);
		keyValues = new ArrayList<>();
	}
	
	public void setAnotatorClass (SourceElement anotator)
	{
		this.anotatorClass = anotator;
		declaration = new ClassDeclaration(anotator);
	}
	
	public String getRequiredPackage() {
		return this.declaration.packageID;
	}
	
	public void addKeyValue(String key, String Value) {
		this.keyValues.add(new anotationKeyValue(key, Value));
	}
	
	public Collection<String> getAnnotationsLines ()
	{
		Collection<String> tmp = new ArrayList<>();
		if (keyValues.size() > 0)
		{
			tmp.add("@"+anotatorClass.getName().replace(".java","")+"(");
			int i = 0;
			for (anotationKeyValue a :keyValues)
			{
				if (i == keyValues.size()-1) tmp.add("\t"+a.key+" = "+a.value+",");
				else tmp.add("\t"+a.key+" = "+a.value+")");
			}
		}
		return tmp;
	}
}
