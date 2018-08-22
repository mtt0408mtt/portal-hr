package com.pm.portal.feign.jaxrs;


import feign.Feign;
import feign.jaxrs.JAXRSContract;

public class RsMain {

	public static void main(String[] args) {
		RsClient client = Feign.builder()
				.contract(new JAXRSContract())//指定注解翻译器
				.target(RsClient.class,
				"http://localhost:8080");
		String result = client.hello();
		System.out.println(result);
	}

}
