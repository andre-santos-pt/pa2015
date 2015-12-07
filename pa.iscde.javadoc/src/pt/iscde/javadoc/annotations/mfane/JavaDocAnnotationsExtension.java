package pt.iscde.javadoc.annotations.mfane;

import java.util.List;

import pa.iscde.javadoc.parser.export.JavaDocNamedTagI;
import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;

public interface JavaDocAnnotationsExtension {
	
	public List<JavaDocNamedTagI> getNamedTags();
	public List<JavaDocUnnamedTagI> getUnnamedTags();
}