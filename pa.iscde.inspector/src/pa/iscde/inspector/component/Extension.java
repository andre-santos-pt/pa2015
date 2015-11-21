package pa.iscde.inspector.component;

import javax.swing.text.html.Option;

import com.google.common.base.Optional;

public class Extension {

	private String id;
	private String name;
	private String point;
	private String clazz;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPoint() {
		return point;
	}
	public String getClazz() {
		return clazz;
	}

	public Extension(String id, String name, String point, String clazz) {
		this.id = id;
		this.name = name;
		this.point = point;
		this.clazz = clazz;
	}
	@Override
	public String toString() {
		
		return "Extension: id = " + id +" - name = " + name + " - point = " + point + " . class = " + clazz;
	}

}
