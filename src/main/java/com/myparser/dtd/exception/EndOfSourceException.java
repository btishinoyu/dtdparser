package com.myparser.dtd.exception;

public class EndOfSourceException extends Exception {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1L;
	
	public EndOfSourceException(String src, int position) {
		super(src.length() + "文字の文字列の" + position + "番目が指定されました。");
	}

}
