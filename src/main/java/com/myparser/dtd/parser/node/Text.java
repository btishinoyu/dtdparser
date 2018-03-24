package com.myparser.dtd.parser.node;

public class Text implements Node {
	private String text;
	
	public Text(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
