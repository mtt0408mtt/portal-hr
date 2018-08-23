package com.pm.portal.access;

import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.redis.AccessKey;
import com.pm.portal.redis.RedisService;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.base.MhAdminService;



@Service
public class AccessInterceptor  extends HandlerInterceptorAdapter{
	
	//HHA
	@Autowired
	MhAdminService adminService;
	
	@Autowired
	RedisService redisService;
	//    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			System.out.println("handler,");
			MhAdmin admin = getAdmin(request, response);
			AdminContext.setAdmin(admin);
			HandlerMethod hm = (HandlerMethod)handler;
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
			if(accessLimit == null) {
				return true;
			}
			int seconds = accessLimit.seconds();
			int maxCount = accessLimit.maxCount();
			boolean needLogin = accessLimit.needLogin();
			String key = request.getRequestURI();
			if(needLogin) {
				if(admin == null) {
					render(response, CodeMsg.SESSION_ERROR);
					return false;
				}
				key += "_" + admin.getId_();
			}else {
				//do nothing
			}
			AccessKey ak = AccessKey.withExpire(seconds);
			Integer count = redisService.get(ak, key, Integer.class);
	    	if(count  == null) {
	    		 redisService.set(ak, key, 1);
	    	}else if(count < maxCount) {
	    		 redisService.incr(ak, key);
	    	}else {
	    		render(response, CodeMsg.ACCESS_LIMIT_REACHED);
	    		return false;
	    	}
		}
		return true;
	}
	
	private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		String str  = JSON.toJSONString(Result.error(cm));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	private MhAdmin getAdmin(HttpServletRequest request, HttpServletResponse response) {
		String paramToken = request.getParameter(adminService.COOKI_NAME_TOKEN);
		String cookieToken = getCookieValue(request, adminService.COOKI_NAME_TOKEN);
		if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			return null;
		}
		String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
		System.out.println("token======,"+token);
		return adminService.getByToken(response, token);
	}
	
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(cookies == null || cookies.length <= 0){
			return null;
		}
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(cookiName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
