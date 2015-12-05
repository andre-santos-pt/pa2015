package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public class AuthorTag implements JavaDocUnnamedTagI {

	@Override
	public String headerName() {
		return "Author";
	}

	@Override
	public String tagName() {
		return "author";
	}
}