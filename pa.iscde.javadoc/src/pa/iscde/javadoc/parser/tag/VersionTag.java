package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class VersionTag implements JavaDocUnnamedTagI {

	@Override
	public String headerName() {
		return "Version";
	}

	@Override
	public String tagName() {
		return "version";
	}
}