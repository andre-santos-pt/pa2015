package pa.iscde.outlaw.jorge;

import java.lang.reflect.Modifier;
import org.eclipse.jdt.core.dom.Type;

public class OutlineField implements OutlineLookup{

	private String name;
	private String visibility;
	private Type type;
	private OutlineClass parent;
	private boolean isStatic;
	private boolean isFinal;
	private String imgName="";
	private boolean isConstant;
	//private int bitwiseoperand=0;
	
	public OutlineField(String name, Type type, int modifiers, OutlineClass clazz) {
		setName(getFieldName(name));
		setType(type);
		setParent(clazz);
		checkVisibility(modifiers);
		checkProperties(modifiers);
		//setImg("");
		setConstant(false);
	}
	
	public OutlineField(String name, OutlineClass clazz){
		setName(name);
		setType(null);
		setParent(clazz);
		setStatic(true);
		setFinal(true);
		setConstant(true);
	}

	private String getFieldName(String name) {
		int argNumber=(name.length() - name.replaceAll(" ", "").length());
		return name.split(" ")[argNumber];
	}

	public OutlineClass getParent() {
		return parent;
	}

	public void setParent(OutlineClass parent) {
		this.parent = parent;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		if(isConstant)
			return getName();
		return getName()+" : "+getType();
	}

	public boolean isStatic() {
		return isStatic;
	}

	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}

	private void checkVisibility(int value){
		
		if(Modifier.isProtected(value)){
			setVisibility("Protected");//00
			//bitwiseoperand|=0;
			setImg("field_protected_obj.gif");
		}else if(Modifier.isPrivate(value)){
			setVisibility("Private");//01
			//bitwiseoperand|=1;
			setImg("field_private_obj.gif");
		}else if(Modifier.isPublic(value)){
			setVisibility("Public");//10
			//bitwiseoperand|=2;
			setImg("field_public_obj.gif");
		}else{
			setVisibility("Package private");//11
			//bitwiseoperand|=3;
		}
	}

	private void checkProperties(int value){
		if(Modifier.isFinal(value)){
			setFinal(true);//1xx ou 0xx
			//bitwiseoperand|=4;
		}
		if(Modifier.isStatic(value)){
			setStatic(true);//1xxx ou 0xxx
			//bitwiseoperand|=8;
		}
	}

	@Override
	public void setImg(String imgName) {
		/*if(imgName.length()==0){
			StringBuilder bitwiseresult = new StringBuilder();
		    for(int i = 4; i >= 0 ; i--) {
		        int mask = 1 << i;
		        bitwiseresult.append((bitwiseoperand & mask) != 0 ? "1" : "0");
		    }
		    bitwiseresult.replace(bitwiseresult.length() - 1, bitwiseresult.length(), "");
		    System.out.println(bitwiseresult.toString());
		    switch(Integer.parseInt(bitwiseresult.toString())){
		    case 0000:
		    	
		    	break;
		    case 0001:
		    	
		    	break;
		    case 0010:
		    	
		    	break;
		    case 0011:
		    	
		    	break;
		    case 0100:
		    	
		    	break;
		    case 0101:
		    	
		    	break;
		    case 0110:
		    	
		    	break;
		    case 0111:
		    	
		    	break;
		    case 1000:
		    	
		    	break;
		    case 1001:
		    	
		    	break;
		    case 1010:
		    	
		    	break;
		    case 1011:
		    	
		    	break;
		    case 1100:
		    	
		    	break;
		    case 1101:
		    	
		    	break;
		    case 1110:
		    	
		    	break;
		    case 1111:
		    	
		    	break;
		    }
		}else*/ this.imgName=imgName;
	}

	@Override
	public String getImg() {
		return imgName;
	}

	public boolean isConstant() {
		return isConstant;
	}

	public void setConstant(boolean isConstant) {
		this.isConstant = isConstant;
		if(isConstant)
			setImg("constant_co.gif");
	}


}
