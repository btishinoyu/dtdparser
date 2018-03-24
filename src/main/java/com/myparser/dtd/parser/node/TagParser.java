package com.myparser.dtd.parser.node;

import java.util.ArrayList;
import java.util.List;

import com.myparser.dtd.exception.EndOfSourceException;
import com.myparser.dtd.parser.Source;

public class TagParser {
	public static List<Node> parse(Source source) {
		List<Node> nodeList = new ArrayList<>();

		try {
			StringBuilder sb = new StringBuilder();
			// 開始タグ検知フラグ
			boolean startTag = false;
			// テキスト開始文字（" または '）
			char startText = Character.MIN_VALUE;
			// ソース検査
			while (source.hasMore()) {
				char c = source.get();
				sb.append(c);
				boolean isComment = sb.indexOf("<!--") == 0;
				if (startTag) {
					switch (c) {
					case '>':
						if (isComment) {
							// コメントタグ終了
							if (sb.lastIndexOf("-->") != -1) {
								startTag = false;
								sb =  new StringBuilder();
							}
						}
						// テキスト内のタグ終了文字ではない場合
						else if (startText == Character.MIN_VALUE) {
							startTag = false;
							Tag tag = createTag(sb.substring(1, sb.length() - 1));
							sb =  new StringBuilder();
							nodeList.add(tag);
						}
						// テキスト内のタグ終了文字の場合
						else {
							// 何もなし
						}
						break;
					case '"':
					case '\'':
						// テキスト開始の場合
						if (startText == Character.MIN_VALUE) {
							startText = c;
						}
						else {
							// テキスト終了
							if (startText == c) {
								startText = Character.MIN_VALUE;
							}
						}
					break;
					}
				}
				else {
					if (!isComment && (c == '<')) {
						startTag = true;
						// タグ外に文字がある場合
						if (sb.length() != 1) {
							Text text = new Text(sb.toString().substring(0, sb.length() - 1));
							nodeList.add(text);
							sb = new StringBuilder();
							sb.append(c);
						}
					}
				}
			}
		} catch (EndOfSourceException e) {
			e.printStackTrace();
		}

		return nodeList;
	}

	private static Tag createTag(String contents) {
		// 最初の空白まで、または空白が無い場合は内容全てがTag名
		int idx = contents.indexOf(" ");
		if (idx == -1) {
			idx = contents.length();
		}
		String name = contents.substring(0, idx);
		
		return new Tag(contents, name);
	}
}
