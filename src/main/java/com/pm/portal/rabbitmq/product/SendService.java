package com.pm.portal.rabbitmq.product;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface SendService {

	@Output("test1")
	SubscribableChannel sendOrder();
}
