package pa.iscde.inspector.component;

import java.util.List;

import com.google.common.base.Splitter;

public class Extension {

	private String id;
	private String name;
	private String point;
	private String clazz;
	private String connectionName;
	private ExtensionPoint extensionPoint;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getConnectionName() {
		if (connectionName == null) {
			connectionName = name + " -> " + extensionPoint.getName();
		}
		return connectionName;
	}

	public ExtensionPoint getExtensionPoint() {
		return extensionPoint;
	}

	public String getPoint() {
		return point;
	}

	public String getClazz() {
		return clazz;
	}

	public void setExtensionPoint(ExtensionPoint extensionPoint) {
		this.extensionPoint = extensionPoint;
	}

	public Extension(String id, String name, String point, String clazz) {
		this.id = id;
		this.point = point;
		this.name = verifieName(name);

		this.clazz = clazz;
	}

	private String verifieName(String name2) {
		String name = name2 != null ? name2 : id;
		if (name == null) {
			Iterable<String> split = Splitter.on(".").split(point);
			for (String string : split) {
				name = string;
			}
		}

		return name;
	}

	@Override
	public String toString() {

		return "Extension: id = " + id + " - name = " + name + " - point = " + point + " . class = " + clazz;
	}

	public void findConnections(List<ExtensionPoint> eXTPOINTS) {
		String[] split = point.split("\\.");
		String stringToCompare = "";
		if (split.length > 0) {
			stringToCompare = split[split.length - 1];
		}
		for (ExtensionPoint extensionPoint : eXTPOINTS) {
			if (extensionPoint.getId().equals(stringToCompare)) {
				this.extensionPoint = extensionPoint;

			}
		}
	}

}
