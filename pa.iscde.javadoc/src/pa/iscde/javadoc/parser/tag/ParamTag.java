package pa.iscde.javadoc.parser.tag;

import pa.iscde.javadoc.parser.export.JavaDocNamedTagI;

public class ParamTag implements JavaDocNamedTagI{

	@Override
	public String tagName() {
		return "param";
	}

	@Override
	public String headerName() {
		return "Parameters";
	}
}