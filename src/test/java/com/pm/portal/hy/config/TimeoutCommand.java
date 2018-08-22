package com.pm.portal.hy.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class TimeoutCommand extends HystrixCommand<String> {

	public TimeoutCommand() {
	    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
	            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
	                   .withExecutionTimeoutInMilliseconds(2000)));//设置超时时间
	}

	@Override
	protected String run() throws Exception {
		Thread.sleep(3000);
		System.out.println("执行命令");
		return "success";
	}

	@Override
	protected String getFallback() {
		System.out.println("执行回退方法");
		return "fallback";
	}
	
	
}
