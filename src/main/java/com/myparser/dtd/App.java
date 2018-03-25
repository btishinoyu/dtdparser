package com.myparser.dtd;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.myparser.dtd.parser.DtdParser;
import com.myparser.dtd.parser.Source;
import com.myparser.dtd.parser.node.Node;

public class App {

	public static void main(String[] args) throws IOException {
		String src = FileUtils.readFileToString(new File("src/main/resources/struts-config_1_2.dtd"),
				StandardCharsets.UTF_8);
		Source source = new Source(src);
		
		List<Node> nodeList = DtdParser.parse(source);
		for (Node node : nodeList) {
			String text = node.print();
			if (StringUtils.isNotEmpty(text)) {
				System.out.println(text);
			}
		}
	}

}
