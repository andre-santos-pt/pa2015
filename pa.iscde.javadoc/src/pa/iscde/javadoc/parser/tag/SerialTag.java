package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.internal.JavaDocTagI;

public class SerialTag implements JavaDocTagI {

	@Override
	public String headerName() {
		return "Serial";
	}

	@Override
	public String tagName() {
		return "serial";
	}
}