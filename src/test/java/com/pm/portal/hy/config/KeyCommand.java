package com.pm.portal.hy.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class KeyCommand extends HystrixCommand<String> {

	public KeyCommand() {
		super(
				Setter.withGroupKey(
						HystrixCommandGroupKey.Factory.asKey("TestGroupKey")) //如果不提供线程池的名称 就用主名称做为线程池的key
						.andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
						.andThreadPoolKey(
								HystrixThreadPoolKey.Factory.asKey("PoolKey")));
	}

	@Override
	protected String run() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
