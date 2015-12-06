package pa.iscde.javadoc.parser.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;

import pa.iscde.javadoc.parser.JavaDocParser;

public class JavaDocBlock {

	private String description;
	private Multimap<String, JavaDocAnnotation> annotations;

	public JavaDocBlock() {
	}

	public Multimap<String, JavaDocAnnotation> getAnnotations() {
		return annotations;
	}
	
	public Map<String, ArrayList<JavaDocAnnotation>> getAnnotationsMapWrapper() {
		Map<String, ArrayList<JavaDocAnnotation>> map = new HashMap<String, ArrayList<JavaDocAnnotation>>();

		for (String tag : annotations.keySet()) {
			ArrayList<JavaDocAnnotation> anotList = new ArrayList<>();
			anotList.addAll(annotations.get(tag));
			map.put(tag, anotList);
		}
		
		return map;
	}

	public void setAnnotations(Multimap<String, JavaDocAnnotation> annotations) {
		this.annotations = annotations;
	}

	public List<String> getTags() {
		if (this.annotations != null) {
			List<String> myTagsOrdered = JavaDocParser.orderedTags();
			myTagsOrdered.retainAll(this.annotations.keySet());
			return myTagsOrdered;
		}
		return null;
	}

	public Collection<JavaDocAnnotation> getAnnotations(String tag) {
		return this.annotations.get(tag);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}