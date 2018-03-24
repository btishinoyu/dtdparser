package com.myparser.dtd.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myparser.dtd.exception.EndOfSourceException;

/**
 * パース対象文管理クラス
 */
public class Source {
	/**
	 * 原文
	 */
	private final String src;
	
	/**
	 * 読み込み位置
	 */
	private int position;

	/**
	 * コンストラクタ
	 * @param src
	 */
	public Source(String src) {
		this.src = src;
		position = 0;
	}
	
	/**
	 * 1文字取得
	 * 
	 * <p>1文字取得後、読み込み位置を1つ次へ移動する。
	 * 
	 * @return
	 * @throws EndOfSourceException
	 */
	public char get() throws EndOfSourceException {
		if (!hasMore()) {
			throw new EndOfSourceException("", position);
		}
		
		return src.charAt(position++);
	}
	
	/**
	 * 次の1文字を取得可能か判定
	 * @return
	 */
	public boolean hasMore() {
		return src.length() > position;
	}
	
	/**
	 * 指定したパターンに一致する箇所まで読み込み位置を移動する。
	 * @param regex 指定パターンの正規表現
	 * @return 移動位置までの文字列
	 */
	public String move(String regex) {
		String retStr = null;
		
		Pattern pattern = Pattern.compile(regex);
		String input = src.substring(position);
		Matcher match = pattern.matcher(input);
		if (match.find()) {
			int matchPos = match.start();
			retStr = src.substring(position, matchPos - 1);
			position = matchPos - 1;
		}
		
		return retStr;
	}
}
