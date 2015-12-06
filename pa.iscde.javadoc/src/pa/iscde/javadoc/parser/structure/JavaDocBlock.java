package pa.iscde.javadoc.parser.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Multimap;

import pa.iscde.javadoc.parser.JavaDocAnnotation;
import pa.iscde.javadoc.parser.JavaDocParser;

public class JavaDocBlock {

	private String description;
	private Multimap<String, JavaDocAnnotation> annotations;

	public JavaDocBlock() {
	}
	
	public HashMap<String, ArrayList<JavaDocAnnotation>> getAnnotationsMap(){
		HashMap<String, ArrayList<JavaDocAnnotation>> map = new HashMap<>();
		for (String tag : annotations.keySet()) {
			ArrayList<JavaDocAnnotation> aux = new ArrayList<>();
			aux.addAll(annotations.get(tag));
			map.put(tag, aux);
		}
		return map;
	}

	public Multimap<String, JavaDocAnnotation> getAnnotations() {
		return annotations;
	}
	
	public void setAnnotations(Multimap<String, JavaDocAnnotation> annotations) {
		this.annotations = annotations;
	}

	public List<String> getTags() {
		List<String> myTagsOrdered = JavaDocParser.orderedTags();
		myTagsOrdered.retainAll(this.annotations.keySet());
		return myTagsOrdered;
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