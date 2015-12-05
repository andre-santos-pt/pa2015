package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class SinceTag implements JavaDocUnnamedTagI {

	@Override
	public String headerName() {
		return "Since";
	}

	@Override
	public String tagName() {
		return "since";
	}
}