package pt.iscde.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

public class ClassRepresentation {
	
	private static final String BREAK = System.getProperty("line.separator");
	
	private EClassType type;
	private String name;
	private List<ClassAttribute> attributes;
	private List<ClassMethod> methods;
	private List<ClassRepresentation> implementedInterfaces;
	private ClassRepresentation superClass;

	public ClassRepresentation() {
		this.attributes = new ArrayList<ClassAttribute>();
		this.methods = new ArrayList<ClassMethod>();
		this.implementedInterfaces = new ArrayList<ClassRepresentation>();
	}

	public EClassType getType() {
		return type;
	}

	public void setType(EClassType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClassAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ClassAttribute> attributes) {
		this.attributes = attributes;
	}

	public List<ClassMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<ClassMethod> methods) {
		this.methods = methods;
	}

	public List<ClassRepresentation> getImplementedInterfaces() {
		return implementedInterfaces;
	}

	public void setImplementedInterfaces(List<ClassRepresentation> implementedInterfaces) {
		this.implementedInterfaces = implementedInterfaces;
	}

	public ClassRepresentation getSuperClass() {
		return superClass;
	}

	public void setSuperClass(ClassRepresentation superClass) {
		this.superClass = superClass;
	}

		@Override
		public String toString() {
			
			StringBuilder sb = new StringBuilder(name).append(BREAK);
			if(this.attributes!=null){
				for (ClassAttribute classAttribute : attributes) {
					sb.append(classAttribute.getVisibility()).append(" ").append(classAttribute.getName()).append(": ").append(classAttribute.getType());
					sb.append(BREAK);
				}
			}
			if(this.methods!=null){
				for (ClassMethod classMethod : methods) {
					sb.append(classMethod.getVisibility()).append(" ").append(classMethod.getName()).append("(");
					for (int i = 0; i < classMethod.getParameterTypes().size(); i++) {
						if(i>0){
							sb.append(", ");
						}
						sb.append(classMethod.getParameterTypes().get(i));
					}
				
					sb.append("): ").append(classMethod.getReturnType());
					sb.append(BREAK);
				}
			}
			return sb.toString();
		}
}
