package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class ReturnTag implements JavaDocUnnamedTagI {

	@Override
	public String tagName() {
		return "return";
	}

	@Override
	public String headerName() {
		return "Returns";
	}
}