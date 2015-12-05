package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class SeeTag implements JavaDocUnnamedTagI {

	@Override
	public String headerName() {
		return "See Also";
	}

	@Override
	public String tagName() {
		return "see";
	}
}