package com.pm.portal.hy.cb;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommand.Setter;

public class FallbackMain {

	public static void main(String[] args) {
		FallbackCommand c = new FallbackCommand();
		String result = c.execute();
		System.out.println(result);
	}

	static class FallbackCommand extends HystrixCommand<String> {
		
		public FallbackCommand() {
		    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
		            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
		                  .withCircuitBreakerForceOpen(true)));//断路器强制打开
		}

		@Override
		protected String run() throws Exception {
			return "success";
		}

		@Override
		protected String getFallback() {
			return "fallback";
		}
	}
}
