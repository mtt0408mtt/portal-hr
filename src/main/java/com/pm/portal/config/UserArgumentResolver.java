package com.pm.portal.config;

import java.io.OutputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.base.MhAdminService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	MhAdminService adminService;

	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz = parameter.getParameterType();
		return clazz == MhAdmin.class;
	}

	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
		log.debug("验证cookie");
		System.out.println("验证cookie");
		String paramToken = request.getParameter(MhAdminService.COOKI_NAME_TOKEN);
		String cookieToken = getCookieValue(request, MhAdminService.COOKI_NAME_TOKEN);
		log.debug("cookieToken "+cookieToken);
		log.debug("paramToken "+paramToken);
		System.out.println("cookieToken "+cookieToken);
		System.out.println("paramToken "+paramToken);
		if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
			log.debug("cookie is null");
			System.out.println("cookie is null");
			render(response, CodeMsg.SESSION_ERROR);
			return null;
		}
		String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
		MhAdmin byToken = adminService.getByToken(response, token);
		if(byToken==null){
			System.out.println("redis查不到token");
			render(response, CodeMsg.SESSION_ERROR);
			return null;
		}
		return byToken;
	}
	
	private String getCookieValue(HttpServletRequest request, String cookiName) {
		Cookie[]  cookies = request.getCookies();
		if(null!=cookies && cookies.length>0){
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(cookiName)) {
					return cookie.getValue();
				}
			}
		}

		return null;
	}
	
	
	private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		String str  = JSON.toJSONString(Result.error(cm));
		out.write(str.getBytes("UTF-8"));
		out.flush();
		out.close();
	}
	
	

}
