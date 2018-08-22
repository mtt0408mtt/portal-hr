package com.pm.portal.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.pm.portal.controller.demo.VPermission;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.domain.portal.MhGroup;
import com.pm.portal.redis.RedisService;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.base.MhAdminService;
import com.pm.portal.vo.LoginVo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

	@Autowired
	MhAdminService adminService;

	@Autowired
	RedisService redisService;

	@RequestMapping("/to_login")
	public String toLogin() {
		return "login";
	}
	


	@RequestMapping("/do_login")
	@ResponseBody
	public Object doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
		// log.info(loginVo.toString());
		// 登录
		return adminService.login(response, loginVo);
	}

	@RequestMapping("/do_quit")
	@ResponseBody
	public Result<String> quit(HttpServletRequest request,HttpServletResponse response) {
		adminService.quit(request,response);
		return Result.ok();
	}
	
	@RequestMapping("/get_token")
	@ResponseBody
	public Object getToken(HttpServletRequest request,HttpServletResponse response,MhAdmin admin) {
		return Result.success(admin);
	}
	
	

	@RequestMapping("/group")
	@ResponseBody
	public Object group(HttpServletResponse response) {
		try {
			
			
			List<MhGroup> group = adminService.group();
			return JSONObject.toJSONString(Result.success(group));
		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.PORTAL_GROUP_ERROR);
		}
	}
	
	
	@RequestMapping("/get_subsys")
	@ResponseBody
	public Object getSubsys(HttpServletResponse response,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			
            List<VPermission> listSubs = adminService.listSubs(vars);
			return JSONObject.toJSONString(Result.success(listSubs));
		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.PORTAL_GROUP_ERROR);
		}
	}
	
	
	
	

}
