package pa.iscde.javadoc.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import pa.iscde.javadoc.internal.JavaDocTagI;
import pa.iscde.javadoc.parser.export.JavaDocNamedTagI;
import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;
import pa.iscde.javadoc.parser.structure.JavaDocBlock;
import pa.iscde.javadoc.parser.tag.AuthorTag;
import pa.iscde.javadoc.parser.tag.DeprecatedTag;
import pa.iscde.javadoc.parser.tag.ParamTag;
import pa.iscde.javadoc.parser.tag.ReturnTag;
import pa.iscde.javadoc.parser.tag.SeeTag;
import pa.iscde.javadoc.parser.tag.SerialTag;
import pa.iscde.javadoc.parser.tag.SinceTag;
import pa.iscde.javadoc.parser.tag.ThrowsTag;
import pa.iscde.javadoc.parser.tag.VersionTag;

/**
 * 
 * @author Miguel
 * @author Nobre
 */
public class JavaDocParser {

	private List<String> orderedTags = new ArrayList<String>();
	private Map<String, JavaDocTagI> tags = new HashMap<String, JavaDocTagI>();

	public JavaDocParser() {

		// Default JavaDocTags
		List<JavaDocTagI> tags = new ArrayList<JavaDocTagI>();
		tags.add(new AuthorTag());
		tags.add(new VersionTag());
		tags.add(new ParamTag());
		tags.add(new ReturnTag());
		tags.add(new ThrowsTag());
		tags.add(new SeeTag());
		tags.add(new SinceTag());
		tags.add(new SerialTag());
		tags.add(new DeprecatedTag());

		for (JavaDocTagI tag : tags) {
			this.addTag(tag);
		}
	}

	public JavaDocParser(List<JavaDocTagI> tags) {
		this();

		for (JavaDocTagI tag : tags) {
			this.addTag(tag);
		}
	}

	private void addTag(JavaDocTagI newTag) {
		this.tags.put(newTag.tagName(), newTag);
		this.orderedTags.add(newTag.tagName());
	}

	public JavaDocBlock parseJavaDoc(String javadoc) {

		JavaDocBlock javaDocBlock = new JavaDocBlock();
		Multimap<String, JavaDocAnnotation> annotations = ArrayListMultimap.create();

		javadoc = javadoc.replace("/** * ", "");
		javadoc = javadoc.replace(" * ", "");
		javadoc = javadoc.replace("*/", "");
		String[] javaDocDetailed = javadoc.split("@");

		String description = javaDocDetailed[0];
		annotations = this.extractAnnotations(javaDocDetailed);

		javaDocBlock.setDesciption(description);
		javaDocBlock.setAnnotations(annotations);

		return javaDocBlock;
	}

	private Multimap<String, JavaDocAnnotation> extractAnnotations(String[] javaDocDetailed) {
		String name = null;
		String description = null;
		JavaDocAnnotation anot = null;

		Multimap<String, JavaDocAnnotation> annotations = ArrayListMultimap.create();

		for (int i = 1; i < javaDocDetailed.length; i++) {
			name = null;
			description = null;

			String tag = javaDocDetailed[i].substring(0, javaDocDetailed[i].indexOf(' '));
			String text = javaDocDetailed[i].substring(javaDocDetailed[i].indexOf(' ') + 1);

			JavaDocTagI anotTag = this.tags.get(tag);

			if (anotTag != null) {
				if (anotTag instanceof JavaDocNamedTagI) {
					name = text.substring(0, text.indexOf(' '));
					description = text.substring(text.indexOf(' ') + 1);
				} else if (anotTag instanceof JavaDocUnnamedTagI) {
					description = text;
				}

				anot = new JavaDocAnnotation(tag, name, description);
				annotations.put(anot.getTag(), anot);
			}
		}

		return annotations;
	}

	public String printJavaDoc(JavaDocBlock javaDocBlock) {
		StringBuilder sb = new StringBuilder();

		sb.append(javaDocBlock.getDesciption() + "\n");

		for (String tag : this.orderedTags) {
			for (JavaDocAnnotation anot : javaDocBlock.getAnnotations(tag)) {
				if (anot.getName() == null || anot.getName().equals("")) {
					sb.append("@" + anot.getTag() + " " + anot.getDescription() + "\n");
				} else {
					sb.append("@" + anot.getTag() + " " + anot.getName() + " - " + anot.getDescription() + "\n");
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {

		String javadoc = "/** * Create a CalculatorGUI with the given title * @param x the window title * @param y the window title */";
		String javadoc2 = "/** * Classe SOKOBANGUI * @author João Paulo Barros * @version 2014/05/05 */";
		String javadoc3 = "/** * Processe Something * @deprecated ups " + "* @param annotations Anot ksjks jsjk sjk "
				+ "* @param roundEnv Env s ksks " + "* @return A piece of shit " + "* @throws RuntimeException "
				+ "* @throws Exception " + "* @see http://www.google.pt " + "* @teste Miguel Nobre" + "*/";

		JavaDocParser javaDocParser = new JavaDocParser();

		JavaDocBlock javaDocBlock = javaDocParser.parseJavaDoc(javadoc3);
		System.out.println(javaDocParser.printJavaDoc(javaDocBlock));
	}
}
