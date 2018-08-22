package com.pm.portal.feign;

import feign.Feign;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import feign.jaxb.JAXBEncoder;

public class XmlTest {

	public static void main(String[] args) {
		JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder().build();
		// 获取服务接口
		HelloClient client = Feign.builder()
				.encoder(new JAXBEncoder(jaxbFactory))//xml的
				.decoder(new JAXBDecoder(jaxbFactory))
				.target(HelloClient.class, "http://localhost:8080/");
		// 构建参数
		Person p = new Person();
		p.setId(1);
		p.setName("angus");
		// 调用接口并返回结果
		Result result = client.createXMLPerson(p);
		System.out.println(result.getMessage());
	}

}
