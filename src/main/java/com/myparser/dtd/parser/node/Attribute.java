package com.myparser.dtd.parser.node;

public class Attribute implements Node {
	private String elementName;
	private String attrName;
	private String data;
	
	public Attribute(String elementName, String attrName, String data) {
		this.elementName = elementName;
		this.attrName = attrName;
		this.data = data;
	}

	public String getElementName() {
		return elementName;
	}

	public String getAttrName() {
		return attrName;
	}

	public String getData() {
		return data;
	}
}
