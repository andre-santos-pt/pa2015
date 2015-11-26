package pa.iscde.outlaw.jorge;

import java.util.ArrayList;

public class OutlineClass implements OutlineLookup{

	private ArrayList<OutlineMethod> method = new ArrayList<OutlineMethod>();
	private ArrayList<OutlineField> fields = new ArrayList<OutlineField>();
	private String name;
	private OutlineClass parent;
	private String visibility;
	private boolean isStatic;
	private boolean isFinal;
	private String imgType="class_obj.gif";
	private boolean isInterface;
	private boolean isAbstract;
	private String packagezz;
	private boolean isEnum;
	
	
	public OutlineClass(String parentClass, String packagezz) {
		setName(parentClass);
		setPackagezz(packagezz);
	}

	public ArrayList<OutlineMethod> getMethod() {
		return method;
	}
	
	public void setMethod(ArrayList<OutlineMethod> method) {
		this.method = method;
	}
	
	public ArrayList<OutlineField> getFields() {
		return fields;
	}
	
	public void setFields(ArrayList<OutlineField> fields) {
		this.fields = fields;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name.replace(".java", "");
	}

	@Override
	public OutlineClass getParent() {
		return parent;
	}

	@Override
	public void setParent(OutlineClass parent) {
		this.parent=parent;
	}

	@Override
	public String getVisibility() {
		return visibility;
	}

	@Override
	public void setVisibility(String visibility) {
		this.visibility=visibility;
	}

	@Override
	public boolean isStatic() {
		return isStatic;
	}

	@Override
	public void setStatic(boolean isStatic) {
		this.isStatic=isStatic;
	}

	@Override
	public boolean isFinal() {
		return isFinal;
	}

	@Override
	public void setFinal(boolean isFinal) {
		this.isFinal=isFinal;
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public void setImg(String imgType) {
		this.imgType=imgType;
	}

	@Override
	public String getImg() {
		return imgType;
	}

	public boolean isInterface() {

		return isInterface;
	}

	public void setInterface(boolean isInterface) {
		this.isInterface = isInterface;
		if(isInterface){
			setImg("int_obj.gif");
		}
	}

	public String getPackagezz() {
		return packagezz;
	}

	public void setPackagezz(String packagezz) {
		this.packagezz = packagezz;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public boolean isEnum() {
		return isEnum;
	}

	public void setEnum(boolean isEnum) {
		this.isEnum = isEnum;
		if(isEnum)
			setImg("enum_obj.gif");
	}


	
	
}
