package pa.iscde.templates.implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import pa.iscde.templates.model.ClassDeclaration;
import pa.iscde.templates.model.MethodDeclaration;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class AbstractClass implements Iimplement{
	private final SourceElement elemento;
	private Collection<MethodDeclaration> methods;
	private Collection<SourceElement> implementations;
		
	public AbstractClass(SourceElement target) {
		this.elemento = target;
		methods = new ArrayList<>();
		implementations = new ArrayList<>();
		GetMethodsFromSource();
	}
	
	public String getName()
	{
		return elemento.getName().replace(".java", "");
	}
	
	public void addMethod(String m)
	{
		methods.add(new MethodDeclaration(m));
	}
	
	@Override
	public void addImplementation (SourceElement s)
	{
		implementations.add(s);
	}

	public void GetMethodsFromSource() {
		try {
			FileInputStream fstream = new FileInputStream(this.elemento.getFile());
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			boolean methods=false;
			while ((strLine = br.readLine()) != null)   {
				  if (methods)
				  {
					  if (strLine.contains("(") && strLine.contains(")") && strLine.contains("abstract"))
					  {
						  this.addMethod(strLine.replace(";", "").replace("abstract", ""));
					  }
				  }
				  if (strLine.contains("{") && methods == false) methods = true;
				}
			br.close();
			fstream.close();
		}
		catch(Exception ex)
		{
			
		}
	}

	@Override
	public Collection<MethodDeclaration> implement(SourceElement target) {
		if (! target.isClass() ) return new ArrayList<>();
		ClassDeclaration cd = new ClassDeclaration(target);
		if (cd.isImplementingInterface() && cd.getTargetForImplement() == this.getName())
		{
			Collection<MethodDeclaration> toImplement = new ArrayList<>();
			for (MethodDeclaration s : methods)
			{
				if (isMissingMethod(target, s)) toImplement.add(s);
			}
		}
		return new ArrayList<>();
	}
	
	@Override
	public Collection<MethodDeclaration> implement(File target) {
		ClassDeclaration cd = new ClassDeclaration(target);
		if (!cd.isClassDeclaration()) return new ArrayList<>();

		if (cd.isImplementingAbstract() && cd.getTargetForImplement().equals(this.getName()))
		{
			Collection<MethodDeclaration> toImplement = new ArrayList<>();
			for (MethodDeclaration s : methods)
			{
				if (isMissingMethod(target, s)) toImplement.add(s);
			}
			return toImplement;
		}
		return new ArrayList<>();
	}

	private boolean isMissingMethod(SourceElement target, MethodDeclaration s) {
		return this.isMissingMethod(target.getFile(), s);
	}
	
	private boolean isMissingMethod(File target, MethodDeclaration s) {
		try {
			FileInputStream fstream = new FileInputStream(target);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			boolean add=true;
			while ((strLine = br.readLine()) != null)   {
				  if (strLine.trim().contains(s.declaration.trim())) 
				  {
					  add = false;
					  break;
				  }
				}
			br.close();
			fstream.close();
			return add;
		}
		catch(Exception ex)
		{

		}
		return false;
	}

	@Override
	public SourceElement getSource() {
		return elemento;
	}

	@Override
	public Collection<SourceElement> getImplementations() {
		return implementations;
	}
}
