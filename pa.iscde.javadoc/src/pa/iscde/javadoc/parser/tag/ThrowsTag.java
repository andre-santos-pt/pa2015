package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class ThrowsTag implements JavaDocUnnamedTagI {

	@Override
	public String headerName() {
		return "Throws";
	}

	@Override
	public String tagName() {
		return "throws";
	}
}