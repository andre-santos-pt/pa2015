package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class ThrowsTag implements JavaDocUnnamedTagI {

	@Override
	public String getHeaderName() {
		return "Throws";
	}

	@Override
	public String getTagName() {
		return "throws";
	}
}