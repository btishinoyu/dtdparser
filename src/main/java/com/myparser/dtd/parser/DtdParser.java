package com.myparser.dtd.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.myparser.dtd.parser.node.Attribute;
import com.myparser.dtd.parser.node.Element;
import com.myparser.dtd.parser.node.Entity;
import com.myparser.dtd.parser.node.Node;
import com.myparser.dtd.parser.node.Tag;
import com.myparser.dtd.parser.node.TagParser;

public class DtdParser {
	public static List<Node> parse(Source source) {
		List<Entity> entityList = new ArrayList<>();
		Map<String, Element> elementMap = new LinkedHashMap<>();
		List<Attribute> attrList = new ArrayList<>();

		List<Node> nodeList = TagParser.parse(source);
		for (Node node : nodeList) {
			if (node instanceof Tag) {
				Tag tag = (Tag) node;
				switch (tag.getTagName().toUpperCase()) {
				case "!ENTITY":
					Entity entity = parseEntity(tag);
					entityList.add(entity);
					break;
				case "!ELEMENT":
					Element elm = parseElement(tag);
					elementMap.put(elm.getName(), elm);
					break;
				case "!ATTLIST":
					Attribute attr = parseAttlist(tag);
					attrList.add(attr);
					break;
				}
			}
		}

		// 各Elementに対応する属性を追加
		for (Attribute attr : attrList) {
			Element elm = elementMap.get(attr.getElementName());
			elm.getAttrList().add(attr);
		}
		// 各Elementに対応する子Elementを設定
		for (Element elm : elementMap.values()) {
			for (String childElmName : elm.getChildNameList()) {
				Element childElm = elementMap.get(childElmName);
				if (childElm != null) {
					elm.getChildElmentList().add(childElm);
				}
			}
		}

		// NodeリストにEntityとElementを統合
		List<Node> dtdNodeList = new ArrayList<>();
		dtdNodeList.addAll(entityList);
		dtdNodeList.addAll(elementMap.values());

		return dtdNodeList;
	}

	private static Entity parseEntity(Tag tag) {
		String tagContents = tag.getContents();
		// タグ名をスキップ
		tagContents = nextWord(tagContents);

		// Entity名
		String name = tagContents.substring(0, tagContents.indexOf(" "));
		tagContents = nextWord(tagContents);
		// パラメータ実装の場合
		if (name.equals("%")) {
			name = "%" + tagContents.substring(0, tagContents.indexOf(" "));
			tagContents = nextWord(tagContents);
		}
		// Entityの残りの要素
		String contents = tagContents;

		// Entity作成
		Entity entity = new Entity(name, contents);

		return entity;
	}

	private static Element parseElement(Tag tag) {
		String tagContents = tag.getContents();
		// タグ名をスキップ
		tagContents = nextWord(tagContents);

		// Element名
		String name = tagContents.substring(0, tagContents.indexOf(" "));
		tagContents = nextWord(tagContents);

		// Elementの内容
		String content = tagContents;

		// Element作成
		Element element = new Element(name, content);
		// 子Elementを登録
		tagContents = tagContents.substring(1, content.length() - 1);
		String[] childs = tagContents.split("[,|\\s]");
		for (String child : childs) {
			if (StringUtils.isNotEmpty(child) && !child.startsWith("#")) {
				if (Pattern.matches(".+[\\+\\?\\*]$", child)) {
					child = child.substring(0, child.length() - 1);
				}
				element.getChildNameList().add(child);
			}
		}

		return element;
	}

	private static Attribute parseAttlist(Tag tag) {
		String tagContents = tag.getContents();
		// タグ名をスキップ
		tagContents = nextWord(tagContents);

		// Element名
		String elementName = tagContents.substring(0, tagContents.indexOf(" "));
		tagContents = nextWord(tagContents);

		// Attribute名
		String attrName = tagContents.substring(0, tagContents.indexOf(" "));
		tagContents = nextWord(tagContents);

		// Attributeのデータタイプ等諸々
		String data = tagContents;

		// Attribute作成
		Attribute attr = new Attribute(elementName, attrName, data);

		return attr;
	}

	private static String nextWord(String str) {
		str = str.substring(str.indexOf(" ") + 1);
		str = str.replaceAll("^ +", "");
		return str;
	}
}
