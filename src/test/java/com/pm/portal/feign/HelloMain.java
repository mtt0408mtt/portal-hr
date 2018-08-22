package com.pm.portal.feign;

import feign.Feign;

public class HelloMain {

	public static void main(String[] args) {
		HelloClient client = Feign.builder().target(HelloClient.class,
				"http://localhost:8080");
		String result = client.hello(); //必须定义一个接口 用feign调用
		System.out.println(result);
	}

}
