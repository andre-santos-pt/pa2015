package pt.iscde.classdiagram.model;

import java.util.ArrayList;
import java.util.List;

public class ClassMethod {
	private EVisibility visibility;
	private String name;
	private String returnType;
	private List<String> parameterTypes;
	
	public ClassMethod() {
		this.parameterTypes = new ArrayList<>();
	}
	
	public EVisibility getVisibility() {
		return visibility;
	}
	public void setVisibility(EVisibility visibility) {
		this.visibility = visibility;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public List<String> getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(List<String> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	
	
}
