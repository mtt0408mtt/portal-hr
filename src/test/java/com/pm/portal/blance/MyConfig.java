package com.pm.portal.blance;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class MyConfig {

	@Autowired(required = false) //??
	@MyLoadBalanced
	private List<RestTemplate> tpls = Collections.emptyList();
	
	@Bean
	public SmartInitializingSingleton lbInitializing() { //加入bean 容器初始化化创建
		return new SmartInitializingSingleton() {

			public void afterSingletonsInstantiated() {//初始化后就会创建
				for(RestTemplate tpl : tpls) {
					List<ClientHttpRequestInterceptor> interceptors = tpl.getInterceptors();
					interceptors.add(new MyInterceptor());
					tpl.setInterceptors(interceptors); //容器启动 加入拦截器
				}
			}
			
		};
	}
}
