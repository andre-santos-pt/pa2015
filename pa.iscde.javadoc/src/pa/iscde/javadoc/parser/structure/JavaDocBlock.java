package pa.iscde.javadoc.parser.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Multimap;

import pa.iscde.javadoc.parser.JavaDocAnnotation;

public class JavaDocBlock {

	private String desciption;
	private Multimap<String, JavaDocAnnotation> annotations;

	public JavaDocBlock() {
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}
	
	public void setAnnotations(Multimap<String, JavaDocAnnotation> annotations) {
		this.annotations = annotations;
	}

	public List<String> getTags() {
		return new ArrayList<String>(this.annotations.keySet());
	}

	public Collection<JavaDocAnnotation> getAnnotations(String tag) {
		return this.annotations.get(tag);
	}
}