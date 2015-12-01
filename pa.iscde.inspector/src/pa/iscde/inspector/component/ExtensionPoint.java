package pa.iscde.inspector.component;

import pa.iscde.inspector.gui.ComponentDisign;

public class ExtensionPoint {
	
	private String id;
	private String name;
	private String schema;
	private Component owner;
	private ComponentDisign ownerDesign;
	
	public ExtensionPoint(String id, String name, String schema,Component owner) {
		this.id = id;
		this.name = name;
		this.schema = schema;
		this.owner = owner;
	}
	
	public void setOwnerDesign(ComponentDisign componentDisign) {
		this.ownerDesign = componentDisign;
	}
	public ComponentDisign getOwnerDesign() {
		return ownerDesign;
	}
	
	public Component getOwner() {
		return owner;
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
		
		return "ExtensionPoint: id = " + id +" - name = " + name + " - schema = " + schema + "  owner : " + owner.getName();
	}

}
