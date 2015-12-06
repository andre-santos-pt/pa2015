package pa.iscde.javadoc.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.service.log.LogService;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import pa.iscde.javadoc.internal.JavaDocServiceLocator;
import pa.iscde.javadoc.parser.export.JavaDocNamedTagI;
import pa.iscde.javadoc.parser.export.JavaDocUnnamedTagI;
import pa.iscde.javadoc.parser.structure.JavaDocAnnotation;
import pa.iscde.javadoc.parser.structure.JavaDocBlock;
import pa.iscde.javadoc.parser.structure.JavaDocTagI;
import pa.iscde.javadoc.parser.tag.AuthorTag;
import pa.iscde.javadoc.parser.tag.DeprecatedTag;
import pa.iscde.javadoc.parser.tag.ParamTag;
import pa.iscde.javadoc.parser.tag.ReturnTag;
import pa.iscde.javadoc.parser.tag.SeeTag;
import pa.iscde.javadoc.parser.tag.SerialTag;
import pa.iscde.javadoc.parser.tag.SinceTag;
import pa.iscde.javadoc.parser.tag.ThrowsTag;
import pa.iscde.javadoc.parser.tag.VersionTag;
import pt.iscde.javadoc.annotations.mfane.JavaDocAnnotationsExtension;

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

		integrateExternalAnnotation();
	}

	public JavaDocParser() {
	}

	public JavaDocParser(List<JavaDocTagI> tags) {
		addTags(tags);
	}

	private static void addTags(List<? extends JavaDocTagI> newTags) {
		if (newTags != null) {
			for (JavaDocTagI tag : newTags) {
				addTag(tag);
			}
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

		try {
			for (int i = 1; i < javaDocDetailed.length; i++) {
				name = null;
				description = null;

				String tag = javaDocDetailed[i].substring(0, javaDocDetailed[i].indexOf(' '));
				int endIndex = javaDocDetailed[i].indexOf(' ') + 1;
				String text = javaDocDetailed[i].substring(endIndex == 0 ? javaDocDetailed[i].length() : endIndex);

				JavaDocTagI anotTag = tags.get(tag);

				if (anotTag != null) {
					if (anotTag instanceof JavaDocNamedTagI) {

						name = text.substring(0, text.indexOf(' ') == -1 ? text.length() : text.indexOf(' '));
						endIndex = text.indexOf(' ') + 1;
						description = text.substring(endIndex == 0 ? text.length() : endIndex);

					} else if (anotTag instanceof JavaDocUnnamedTagI) {
						description = text;
					}

					anot = new JavaDocAnnotation(anotTag, name, description);
					annotations.put(anot.getTagName(), anot);
				}
			}
		} catch (Exception e) {
			JavaDocServiceLocator.getLogService().log(LogService.LOG_ERROR, e.getMessage());
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

	private static void integrateExternalAnnotation() {
		IExtensionRegistry extRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extRegistry.getExtensionPoint("pa.iscde.javadoc.annotations");

		IExtension[] extensions = extensionPoint.getExtensions();
		for (IExtension e : extensions) {
			IConfigurationElement[] confElements = e.getConfigurationElements();
			for (IConfigurationElement c : confElements) {
				try {
					JavaDocAnnotationsExtension o = (JavaDocAnnotationsExtension) c.createExecutableExtension("class");
					addTags(o.getNamedTags());
					addTags(o.getUnnamedTags());
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}