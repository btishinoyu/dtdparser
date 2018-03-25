package com.myparser.dtd.parser.node;

public class Tag implements Node {
	private String contents;
	private String tagName;
	
	public Tag(String contents, String tagName) {
		this.contents = contents;
		this.tagName = tagName;
	}

	public String getContents() {
		return contents;
	}

	public String getTagName() {
		return tagName;
	}

	@Override
	public String print() {
		return String.format("%s\t%s", tagName, contents);
	}
}
