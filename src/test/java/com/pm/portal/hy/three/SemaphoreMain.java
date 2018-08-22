package com.pm.portal.hy.three;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

public class SemaphoreMain {

	public static void main(String[] args) throws Exception {
		ConfigurationManager.getConfigInstance().setProperty(
				"hystrix.command.default.execution.isolation.strategy", ExecutionIsolationStrategy.SEMAPHORE);
		ConfigurationManager.getConfigInstance().setProperty(
				"hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests", 3); //设置最大并发数 默认值为10 本例子设置为2
		for(int i = 0; i < 6; i++) {
			final int index = i;
			Thread t = new Thread() {
				public void run() {
					MyCommand c = new MyCommand(index);
					c.execute();
				}
			};
			t.start();
		}
		Thread.sleep(5000);
	}

}
