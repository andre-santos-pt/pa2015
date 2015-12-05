package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class DeprecatedTag implements JavaDocUnnamedTagI {

	@Override
	public String headerName() {
		return "Deprecated";
	}

	@Override
	public String tagName() {
		return "deprecated";
	}
}