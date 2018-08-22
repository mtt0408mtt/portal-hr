package com.pm.portal.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyConfig {

//	@Bean
//	public IRule getRule() {
//		return new MyRule(); //自定义负载均衡规则
//	}
//	
//	@Bean
//	public Contract feignContract() {
//		return new MyContract(); //feign自定义注解翻译器 使用下支持myurl
//	}
	
	
	@Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("10240KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("1024000KB");  
        return factory.createMultipartConfig();  
    }  
	
}




