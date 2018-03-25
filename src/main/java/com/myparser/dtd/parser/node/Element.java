package com.myparser.dtd.parser.node;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Element implements Node {
	private String name;
	private String content;
	private List<String> childNameList;
	private List<Element> childElmentList;
	private List<Attribute> attrList;
	private boolean isChild = false;
	
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

	@Override
	public String print() {
		String text = null;
		if (!isChild) {
			text = print(1);
			text = text.substring(0, text.length() -2);
		}
		return text;
	}
	
	private String print(int tabCount) {
		StringBuilder sb = new StringBuilder();
		
		StringBuilder attrFullSb = new StringBuilder();
		StringBuilder attrSb = new StringBuilder();
		for (Attribute attr : attrList) {
			attrFullSb.append(attr.print()).append(", ");
			attrSb.append(attr.getAttrName()).append(", ");
		}
		sb.append("Element").append("\t");
		sb.append(attrFullSb.toString()).append("\t");
		sb.append(attrSb.toString()).append(StringUtils.repeat('\t', tabCount));
		sb.append(name).append("\t");
		sb.append("\r\n");
		
		for (Element childElm : childElmentList) {
			sb.append(childElm.print(tabCount + 1));
		}
		
		return sb.toString();
	}

	public boolean isChild() {
		return isChild;
	}

	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}
}
