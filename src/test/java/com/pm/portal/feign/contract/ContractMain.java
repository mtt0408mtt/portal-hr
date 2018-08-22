package com.pm.portal.feign.contract;


import feign.Feign;
import feign.jaxrs.JAXRSContract;

public class ContractMain {

	public static void main(String[] args) {
		ContractClient client = Feign.builder()
				.contract(new MyContract())
				.target(ContractClient.class,
				"http://localhost:8080");
		String result = client.hello();
		System.out.println(result);
	}

}
