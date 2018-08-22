package com.pm.portal.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class MyInterceptor implements RequestInterceptor {

	public void apply(RequestTemplate template) {
		template.header("Content-Type", "application/json");//feign的请求拦截器
		System.out.println("这是自定义请求拦截器");
	}

}
