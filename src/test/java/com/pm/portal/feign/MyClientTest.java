package com.pm.portal.feign;

import feign.Feign;

public class MyClientTest {

	public static void main(String[] args) {
		HelloClient client = Feign.builder()
				.client(new MyClient())
				.target(HelloClient.class,
				"http://localhost:8080");
		String result = client.hello();
		System.out.println(result);
	}

}
