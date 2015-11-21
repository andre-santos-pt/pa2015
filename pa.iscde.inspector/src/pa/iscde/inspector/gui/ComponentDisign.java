package pa.iscde.inspector.gui;

import java.awt.Color;
import java.util.List;

import pa.iscde.inspector.component.Component;

public class ComponentDisign{

	private Color color;
	private boolean isAtive;
	private Component component;
	private List<String> links;
	
	
	public ComponentDisign(Component component) {
		this.component = component;
		color = Color.BLUE;
		isAtive = true;
		links = component.getRequiredBundle();
	}
	
	public int numberOfLinks(){
		return links.size();
	}
	public String getName() {
		return component.getName();
	}
	
	public List<String> getLinks() {
		return links;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public boolean isAtive() {
		return isAtive;
	}
	public void setAtive(boolean isAtive) {
		this.isAtive = isAtive;
	}
	
	
	
	
}
