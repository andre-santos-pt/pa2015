package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class ReturnTag implements JavaDocUnnamedTagI {

	@Override
	public String getTagName() {
		return "return";
	}

	@Override
	public String getHeaderName() {
		return "Returns";
	}
}