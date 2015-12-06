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

	private static List<String> orderedTags = new ArrayList<String>();
	private static Map<String, JavaDocTagI> tags = new HashMap<String, JavaDocTagI>();

	static {
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
			addTag(tag);
		}
	}

	public JavaDocParser() {
	}

	public JavaDocParser(List<JavaDocTagI> tags) {
		for (JavaDocTagI tag : tags) {
			addTag(tag);
		}
	}

	private static void addTag(JavaDocTagI newTag) {
		tags.put(newTag.getTagName(), newTag);
		orderedTags.add(newTag.getTagName());
	}

	public JavaDocBlock parseJavaDoc(String javadoc) {

		JavaDocBlock javaDocBlock = new JavaDocBlock();
		Multimap<String, JavaDocAnnotation> annotations = ArrayListMultimap.create();

		javadoc = javadoc.replace("\n", "");
		javadoc = javadoc.replace("*", "");
		javadoc = javadoc.replace("/", "");
		javadoc = javadoc.trim();
		String[] javaDocDetailed = javadoc.split("@");

		String description = javaDocDetailed[0].isEmpty() ? null : javaDocDetailed[0];
		annotations = this.extractAnnotations(javaDocDetailed);

		javaDocBlock.setDescription(description);
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

				anot = new JavaDocAnnotation(anotTag, name, description);
				annotations.put(anot.getTagName(), anot);
			}
		}

		return annotations;
	}

	public String printJavaDoc(JavaDocBlock javaDocBlock) {
		StringBuilder sb = new StringBuilder();

		sb.append(javaDocBlock.getDescription() + "\n");

		for (String tag : orderedTags) {
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

	public static List<String> orderedTags() {
		return new ArrayList<String>(orderedTags);
	}

	public static void main(String[] args) {

		String javadoc = "/** * Create a CalculatorGUI with the given title * @param x the window title * @param y the window title */";
		String javadoc2 = "/** * Classe SOKOBANGUI * @author João Paulo Barros * @version 2014/05/05 */";
		String javadoc3 = "/** * Processe Something * fhfjhdjfhjd @deprecated ups "
				+ "* @param annotations Anot ksjks jsjk sjk " + "* @param roundEnv Env s ksks "
				+ "* @return A piece of shit " + "* @throws RuntimeException " + "* @throws Exception * @lol blabla "
				+ "* @see http://www.google.pt " + "* @teste Miguel Nobre" + "*/";
		String javadoc4 = "/** * efd * @param cxx gggg * @return dsfdsadw */";
		JavaDocParser javaDocParser = new JavaDocParser();

		JavaDocBlock javaDocBlock = javaDocParser.parseJavaDoc(javadoc4);
		System.out.println(javaDocBlock.getDescription());
		System.out.println(javaDocParser.printJavaDoc(javaDocBlock));
	}

}
