package com.myparser.dtd.parser.node;

import java.util.ArrayList;
import java.util.List;

public class Element implements Node {
	private String name;
	private String content;
	private List<String> childNameList;
	private List<Element> childElmentList;
	private List<Attribute> attrList;
	
	public Element(String name, String content) {
		this.name = name;
		this.content = content;
		this.childNameList = new ArrayList<>();
		this.childElmentList = new ArrayList<>();
		this.attrList = new ArrayList<>();
	}

	public List<String> getChildNameList() {
		return childNameList;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public List<Attribute> getAttrList() {
		return attrList;
	}

	public List<Element> getChildElmentList() {
		return childElmentList;
	}
}
