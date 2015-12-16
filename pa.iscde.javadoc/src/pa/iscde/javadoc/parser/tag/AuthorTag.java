package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class AuthorTag implements JavaDocUnnamedTagI {

	@Override
	public String getHeaderName() {
		return "Author";
	}

	@Override
	public String getTagName() {
		return "author";
	}
}