package pa.iscde.templates.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class ClassDeclaration {
	public String fullDeclaration;
	public Collection<String> includes;
	public String packageID;
	public int lines;
	
	public ClassDeclaration(SourceElement elemento)
	{
		this(elemento.getFile());
	}
	
	public ClassDeclaration(File elemento)
	{
		includes = new ArrayList<>();
		String classD = "";;
		lines = 0;
		try {
			FileInputStream fstream = new FileInputStream(elemento);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			boolean isBody = false;
			while ((strLine = br.readLine()) != null)   {
				  if (strLine.contains("package")) packageID = strLine.replace("package ", "").replace(";", "");
				  if (strLine.contains("import")) includes.add(strLine);
				  if (strLine.contains("class") || strLine.contains("interface")) {
					  classD = strLine; 
				  }
				  lines++;
				  if (strLine.contains("{")) isBody=true;
				}
			br.close();
			fstream.close();
		}
		catch(Exception ex)
		{
			
		}
		this.fullDeclaration = classD;
	}
		
	public boolean isClassDeclaration()
	{
		return fullDeclaration.contains("class") ? true : false;
	}
	
	public boolean isInterface()
	{
		return (fullDeclaration.contains("interface") && !fullDeclaration.contains("@")) ? true : false;
	}
	
	public boolean isAnotator()
	{
		return fullDeclaration.contains("@interface") ? true : false;
	}
	
	public boolean isAbstractDeclaration()
	{
		return fullDeclaration.contains("abstract") && fullDeclaration.contains("class") ? true : false;
	}
	
	public boolean isImplementingInterface()
	{
		return fullDeclaration.contains("implements") ? true : false;
	}
	
	public boolean isImplementingAbstract()
	{
		return fullDeclaration.contains("extends") ? true : false;
	}
	
	public String getTargetForImplement()
	{
		if (!isImplementingInterface() && !isImplementingAbstract()) return "";
		String[] blocks = fullDeclaration.replace("{","").trim().split(" ");
		if ( isImplementingInterface() && blocks[blocks.length - 1].contains("implements")) return "";
		else if ( isImplementingAbstract() && blocks[blocks.length - 1].contains("extends")) return "";
		if (blocks[blocks.length - 1].length()>0) return blocks[blocks.length - 1];
		else return "";
	}
}
