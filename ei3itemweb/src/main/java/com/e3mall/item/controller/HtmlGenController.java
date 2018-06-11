package com.e3mall.item.controller;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


@Controller
public class HtmlGenController {


	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@RequestMapping("/genhtml")
	@ResponseBody
	public String genHtml()throws Exception{
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("hello.ftl");
		Map dataModel = new HashMap<>();
		dataModel.put("hello","1000");
		Writer out = new FileWriter(new File("C:\\Users\\lC\\Desktop\\ftl\\spring-freemarker.html"));
		template.process(dataModel,out);
		out.close();
		return "OK";
	}
}
