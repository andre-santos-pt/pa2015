package pa.iscde.templates.coding;

public class DefaultReturns extends TemplateReturns {

	private String myMessage = "//Not implemented yet...";

	@Override
	public String returnVoid() {
		return myMessage;
	}

	@Override
	public String returnByte() {
		return myMessage+"\n"+"return 0;";
	}

	@Override
	public String returnShort() {
		return myMessage+"\n"+"return 0;";
	}

	@Override
	public String returnInt() {
		return myMessage+"\n"+"return 0;";
	}

	@Override
	public String returnLong() {
		return myMessage+"\n"+"return 0L;";
	}

	@Override
	public String returnFloat() {
		return myMessage+"\n"+"return 0.0f;";
	}

	@Override
	public String returnChar() {
		return myMessage+"\n"+"return '\u0000';";
	}

	@Override
	public String returnBoolean() {
		return myMessage+"\n"+"return false;";
	}

	@Override
	public String returnString() {
		return myMessage+"\n"+"return \"\";";
	}

	@Override
	public String returnDefault() {
		return myMessage+"\n"+"return null;";
	}

}
