package com.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


public class FreeMarkerTest {

	@Test
	public void testFreeMarker()throws Exception{
		Configuration configuration = new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("E:\\ideaWorkFile\\ei3\\ei3itemweb\\src\\main\\webapp\\WEB-INF\\ftl"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("hello.ftl");
		Map dataModel = new HashMap<>();
		dataModel.put("hello","this is my first freemarker test.");
		Writer out = new FileWriter(new File("C:\\Users\\lC\\Desktop\\ftl\\hello.html"));
		template.process(dataModel,out);
		out.close();

	}
}
