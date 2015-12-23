package pa.iscde.templates.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */

public class MethodDeclaration {
	public String declaration;
	
	public MethodDeclaration (String txt)
	{
		declaration = txt.replace("\t", "");
	}
	
	public String AccessModifier()
	{
		String[] block = declaration.split(" ");
		if (block[0].contains("public")) return block[0];
		if (block[0].contains("protected")) return block[0];
		if (block[0].contains("private")) return block[0];
		else return "";
	}
	
	public Collection<String> OtherModifiers()
	{
		Collection<String> mods = new ArrayList<>();
		boolean processFirst = this.AccessModifier().length() > 0 ? false : true;
		for ( String s : declaration.split(" "))
		{
			if (processFirst)
			{
				if (s.equals("static")) mods.add(s);
				else if (s.equals("final")) mods.add(s);
				else if (s.equals("abstract")) mods.add(s);
			}
			processFirst = true;
		}
		return mods;
	}
	
	public String returnType ()
	{
		String am = AccessModifier();
		Collection<String> mods = OtherModifiers();
		String tmp = declaration.replace(am,"");
		for (String s : mods)
			tmp = tmp.replace(s, "");
		return tmp.trim().split(" ")[0];
	}
	
	public String methodName ()
	{
		String am = AccessModifier();
		Collection<String> mods = OtherModifiers();
		String tmp = declaration.replace(am,"");
		for (String s : mods)
			tmp = tmp.replace(s, "");
		String block = tmp.trim().split(" ")[1];
		if (block.contains("(")) return block.split("\\(")[0];
		else return block;
	}
	
	public String Parameters ()
	{
		String[] blocks = declaration.split("\\(");
		return "("+blocks[1];
	}
	
	public String getSignature()
	{
		String ret = this.AccessModifier().length() > 0 ? this.AccessModifier() : "public";
		ret += " " + this.returnType();
		ret += " " + this.methodName();
		ret += " " + this.Parameters() + " {";
		return ret.trim();
	}
}
