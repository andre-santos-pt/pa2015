package pa.iscde.inspector.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class PluginXmlParser {

	public List<Extension> extension = new ArrayList<Extension>();
	public List<ExtensionPoint> extensionPoint  = new ArrayList<ExtensionPoint>();
	private Component component;
	public List<Extension> getExtension() {
		return extension;
	}

	public List<ExtensionPoint> getExtensionPoint() {
		return extensionPoint;
	}

	public PluginXmlParser ReadFile(File file, Component comp) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		this.component = comp;
		DocumentBuilder documentBuilder;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			getExtensionFromXml(document);
			getExtensionPointFromXml(document);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	private void getExtensionFromXml(Document document) {
		NodeList extensionNode = document.getElementsByTagName("extension");
		
		for (int i = 0; i< extensionNode.getLength() ; i++) {
			Node item = extensionNode.item(i);
			if(item.getNodeType() == Node.ELEMENT_NODE){
				Element element = (org.w3c.dom.Element) item;
				Node idNode =element.getAttributeNode("id");
				Node nameNode = element.getAttributeNode("name");
				Node  pointNode = element.getAttributeNode("point");
				Node classNode = element.getAttributeNode("class");
				String id = (idNode != null) ? idNode.getTextContent() : null;
				String name = (nameNode != null) ? nameNode.getTextContent() : null;
				String  point = (pointNode != null) ? pointNode.getTextContent() : null;
				String clazz = (classNode != null) ? classNode.getTextContent(): null;

				extension.add(new Extension(id, name, point, clazz));
			}
		}
	}

	private void getExtensionPointFromXml(Document document) {
		NodeList extensionPointNode = document.getElementsByTagName("extension-point");
		
		for (int i = 0; i< extensionPointNode.getLength() ; i++) {
			Node item = extensionPointNode.item(i);
			if(item.getNodeType() == Node.ELEMENT_NODE){
				Element element = (org.w3c.dom.Element) item;
				Node idNode =element.getAttributeNode("id");
				Node nameNode = element.getAttributeNode("name");
				Node  schemaNode = element.getAttributeNode("schema");
				String id = (idNode != null) ? idNode.getTextContent() : null;
				String name = (nameNode != null) ? nameNode.getTextContent() : null;
				String  schema = (schemaNode != null) ? schemaNode.getTextContent() : null;

				extensionPoint.add(new ExtensionPoint(id, name, schema,component));
			}
		}
	}
	
	public static void main(String[] args) {
		PluginXmlParser pluginXmlParser= new PluginXmlParser().ReadFile(new File("C:\\Users\\Jorge\\git\\pidesco\\pt.iscte.pidesco\\plugin.xml"), new Component());
		
		for (Extension extension : pluginXmlParser.getExtension()) {
			System.out.println(extension);
		}
		
		for (ExtensionPoint extension : pluginXmlParser.getExtensionPoint()) {
			System.out.println(extension);
		}

	}

}
