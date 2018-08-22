package com.pm.portal.hy.three;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class MyCommand extends HystrixCommand<String> {
	
	private int index;

	public MyCommand(int index) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory
				.asKey("TestGroupKey")));
		this.index = index;
	}

	@Override
	protected String run() throws Exception {
		Thread.sleep(500);
		System.out.println("执行方法，当前索引：" + index);
		return "";
	}

	@Override
	protected String getFallback() {
		System.out.println("执行回退，当前索引：" + index);
		return "";
	}
	
	
}
