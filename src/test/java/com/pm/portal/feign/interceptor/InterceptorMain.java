package com.pm.portal.feign.interceptor;


import com.pm.portal.feign.HelloClient;

import feign.Feign;

public class InterceptorMain {

	public static void main(String[] args) {
		HelloClient client = Feign.builder()
				.requestInterceptor(new MyInterceptor())
				.target(HelloClient.class,
				"http://localhost:8080");
		String result = client.hello();
		System.out.println(result);
	}

}
