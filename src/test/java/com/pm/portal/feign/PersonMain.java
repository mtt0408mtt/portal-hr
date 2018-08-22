package com.pm.portal.feign;

import feign.Feign;
import feign.gson.GsonDecoder;

public class PersonMain {

	public static void main(String[] args) {
		//first-boot 见这个
		HelloClient client = Feign.builder()
				.decoder(new GsonDecoder()) //解码器把json对象转换为person对象
				.target(HelloClient.class,
				"http://localhost:8080");
		Person p = client.getPerson(1);
		System.out.println(p.getId());
		System.out.println(p.getName());
	}

}
