package pa.iscde.inspector.component;

public class ExtensionPoint {
	
	private String id;
	private String name;
	private String schema;
	
	public ExtensionPoint(String id, String name, String schema) {
		this.id = id;
		this.name = name;
		this.schema = schema;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSchema() {
		return schema;
	}
	
	@Override
	public String toString() {
		
		return "ExtensionPoint: id = " + id +" - name = " + name + " - schema = " + schema;
	}

}
