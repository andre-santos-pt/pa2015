package pa.iscde.templates.coding;

import pa.iscde.templates.model.MethodDeclaration;
/**
 * @author Ricardo Imperial & Filipe Pinho
 *
 */


public interface Ireturn {
	public String textForReturn(MethodDeclaration declaration);
	
	public String returnVoid();
	public String returnByte();
	public String returnShort();
	public String returnInt();
	public String returnLong();
	public String returnFloat();
	public String returnChar();
	public String returnBoolean();
	public String returnString();
	public String returnDefault(MethodDeclaration declaration);
}