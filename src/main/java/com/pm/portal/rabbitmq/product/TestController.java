package com.pm.portal.rabbitmq.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class TestController {
	
	@Autowired
	private SendService sendService;

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String send() {
		Message msg = MessageBuilder.withPayload("Hello World".getBytes()).build();
		boolean send = sendService.sendOrder().send(msg);
		System.out.println("send,"+send);
		return "success";
	}

}
