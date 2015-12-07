package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocNamedTagI;

public class ParamTag implements JavaDocNamedTagI{

	@Override
	public String getTagName() {
		return "param";
	}

	@Override
	public String getHeaderName() {
		return "Parameters";
	}
}