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

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */


public class Interface implements Iimplement{
	private final SourceElement elemento;
	private Collection<MethodDeclaration> methods;
	private Collection<SourceElement> implementations;
	
	/**
	 * Constructor para criação da class interface que recebe como parametro a class alvo.
	 * @param target
	 */
	public Interface(SourceElement target) {
		this.elemento = target;
		methods = new ArrayList<>();
		implementations = new ArrayList<>();
		GetMethodsFromSource();
	}
	
	/**
	 * @return Devolve uma string com o nome da class.
	 */
	@Override
	public String getName()
	{
		return elemento.getName().replace(".java", "");
	}
	
	/**
	 * @param m
	 * @return Adiciona o método a lista de metodos atraves do nome que é parameterizado.
	 */
	public void addMethod(String m)
	{
		methods.add(new MethodDeclaration(m));
	}
	
	/**
	 * @param m
	 * @return Adiciona as classes que se pretende implimentar a interface do nome que é parameterizado.
	 */
	public void addImplementation (SourceElement s)
	{
		implementations.add(s);
	}

	/**
	 * Devolve os metodos da class fonte onde pretendemos ir buscar os métodos.
	 */
	public void GetMethodsFromSource() {
		try {
			FileInputStream fstream = new FileInputStream(this.elemento.getFile());
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			boolean methods=false;
			while ((strLine = br.readLine()) != null)   {
				  if (methods)
				  {
					  if (strLine.contains("(") && strLine.contains(")"))
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

	/**
	 * @param target
	 * @return Implementa os metodos na class alvo que é parameterizada.
	 */
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

	
	/**
	 * @param target
	 * @return Implementa os metodos no ficheiro alvo que é parameterizado.
	 */
	@Override
	public Collection<MethodDeclaration> implement(File target) {
		ClassDeclaration cd = new ClassDeclaration(target);
		if (!cd.isClassDeclaration()) return new ArrayList<>();

		if (cd.isImplementingInterface() && cd.getTargetForImplement().equals(this.getName()))
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

	/**
	 * @param target, s
	 * @return indica se o método já esta declarado na class alvo ou não, usando a class parameterizada e o nome do método correspondente.
	 */
	private boolean isMissingMethod(SourceElement target, MethodDeclaration s) {
		return this.isMissingMethod(target.getFile(), s);
	}
	
	/**
	 * @param target, s
	 * @return indica se o método já esta declarado no ficheiro alvo ou não, usando o ficheiro parameterizado e o nome do método correspondente.
	 */
	private boolean isMissingMethod(File target, MethodDeclaration s) {
		try {
			FileInputStream fstream = new FileInputStream(target);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			boolean add=true;
			while ((strLine = br.readLine()) != null)   {
				  if (strLine.trim().contains(s.getSignature().trim())) {
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

	/**
	 * @return Devolve a Interface que possui os metodos a ser implementados.
	 */
	@Override
	public SourceElement getSource() {
		return elemento;
	}

	/**
	 * @return Devolve a colecção de elementos a ser implementados.
	 */
	@Override
	public Collection<SourceElement> getImplementations() {
		return implementations;
	}
}
