package com.pm.portal.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pm.portal.domain.base.PMAIAdmin;




@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String index(Model model, PMAIAdmin admin){
		 if (admin == null) {
		 return "redirect:/login/to_login";
		 }
		 System.out.println("admin,"+admin.getNickname());
		return "redirect:/index.htm"; //http://localhost:8080/index.htm#/experiment_tree 不拦截 在前端app.js做了拦截
	}
	

}
