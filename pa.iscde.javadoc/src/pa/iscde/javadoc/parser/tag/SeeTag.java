package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class SeeTag implements JavaDocUnnamedTagI {

	@Override
	public String getHeaderName() {
		return "See Also";
	}

	@Override
	public String getTagName() {
		return "see";
	}
}