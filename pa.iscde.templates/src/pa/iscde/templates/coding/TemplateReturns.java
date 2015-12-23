package pa.iscde.templates.coding;

import pa.iscde.templates.model.MethodDeclaration;

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */


public abstract class TemplateReturns implements Ireturn {

	/**
	 * @return Devolve uma string com a mensagem  correspondente ao tipo da declaração do método.
	 */
	@Override
	public final String textForReturn(MethodDeclaration declaration) {
		String declaratedReturn = declaration.returnType();
		if (declaratedReturn.equals("void")) return returnVoid();
		else if (declaratedReturn.equals("byte")) return returnByte();
		else if (declaratedReturn.equals("short")) return returnShort();
		else if (declaratedReturn.equals("int")) return returnInt();
		else if (declaratedReturn.equals("long")) return returnLong();
		else if (declaratedReturn.equals("float")) return returnFloat();
		else if (declaratedReturn.equals("char")) return returnChar();
		else if (declaratedReturn.equals("boolean")) return returnBoolean();
		else if (declaratedReturn.equals("string")) return returnString();
		else return returnDefault(declaration);
	}
	
	@Override
	public abstract String returnVoid();
	
	@Override
	public abstract String returnByte();
	
	@Override
	public abstract String returnShort();
	
	@Override
	public abstract String returnInt();
	
	@Override
	public abstract String returnLong();
	
	@Override
	public abstract String returnFloat();
	
	@Override
	public abstract String returnChar();
	
	@Override
	public abstract String returnBoolean();
	
	@Override
	public abstract String returnString();
	
	@Override
	public abstract String returnDefault(MethodDeclaration declaration);

}
