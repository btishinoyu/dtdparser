package com.myparser.dtd.parser.node;

public class Entity implements Node {
	private String name;
	private String contents;
	
	public Entity(String name, String contents) {
		this.name = name;
		this.contents = contents;
	}

	public String getName() {
		return name;
	}

	public String getContents() {
		return contents;
	}
}
