package com.pm.portal.feign;

import feign.Feign;
import feign.gson.GsonEncoder;

public class JsonTest {

	public static void main(String[] args) {
		HelloClient client = Feign.builder()
				.encoder(new GsonEncoder())//需要编码成json
				.target(HelloClient.class,
				"http://localhost:8080");
		Person p = new Person();
		p.setId(1);
		p.setName("angus");
		String result = client.createPerson(p);
		System.out.println(result);
	}

}
