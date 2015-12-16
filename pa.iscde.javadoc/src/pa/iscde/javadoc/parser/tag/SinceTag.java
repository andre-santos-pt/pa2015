package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class SinceTag implements JavaDocUnnamedTagI {

	@Override
	public String getHeaderName() {
		return "Since";
	}

	@Override
	public String getTagName() {
		return "since";
	}
}