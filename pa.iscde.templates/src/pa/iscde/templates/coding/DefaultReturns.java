package pa.iscde.templates.coding;

import pa.iscde.templates.model.MethodDeclaration;

/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */


public class DefaultReturns extends TemplateReturns {

	private String myMessage = "//Not implemented yet...";

	
	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet.
	 */
	@Override
	public String returnVoid() {
		return myMessage;
	}

	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return 0.
	 */
	@Override
	public String returnByte() {
		return myMessage+"\n"+"return 0;";
	}

	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return 0.
	 */
	@Override
	public String returnShort() {
		return myMessage+"\n"+"return 0;";
	}

	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return 0.
	 */
	@Override
	public String returnInt() {
		return myMessage+"\n"+"return 0;";
	}

	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return 0L.
	 */
	@Override
	public String returnLong() {
		return myMessage+"\n"+"return 0L;";
	}

	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return 0.0f.
	 */
	@Override
	public String returnFloat() {
		return myMessage+"\n"+"return 0.0f;";
	}
	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return de um caracter vazio.
	 */
	@Override
	public String returnChar() {
		return myMessage+"\n"+"return '\u0000';";
	}

	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return false.
	 */
	@Override
	public String returnBoolean() {
		return myMessage+"\n"+"return false;";
	}
	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return "".
	 */
	@Override
	public String returnString() {
		return myMessage+"\n"+"return \"\";";
	}
	/**
	 * @return Devolve uma string com a mensagem  default Not implemented yet seguida de um exemplo de return null.
	 */
	@Override
	public String returnDefault(MethodDeclaration declaration) {
		return myMessage+"\n"+"return null;";
	}

}
