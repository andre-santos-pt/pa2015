package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class VersionTag implements JavaDocUnnamedTagI {

	@Override
	public String getHeaderName() {
		return "Version";
	}

	@Override
	public String getTagName() {
		return "version";
	}
}