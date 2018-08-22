package com.pm.portal.controller.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Demo01Controller {
	
	@Autowired
	private ApplicationContext ctx;
	/*
	 * 获取属性
	 */
	@GetMapping("/prop")
	@ResponseBody //GetMapping 必须和ResponseBody 一起使用
	public String getName(){
		return ctx.getEnvironment().getProperty("test.user.name");
	}

}
