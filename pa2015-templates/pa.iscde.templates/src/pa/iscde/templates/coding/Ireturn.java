package pa.iscde.templates.coding;

import pa.iscde.templates.model.MethodDeclaration;

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
	public String returnDefault();
}