package com.pm.portal.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.1.245");
		Connection conn = factory.newConnection();
		Channel channel = conn.createChannel();
		
		String queueName = "test1";
		boolean durable = true;
		channel.queueDeclare(queueName, false, false, false, null);
		
		String message = "Hello world 2!";
		channel.basicPublish("", queueName, null, message.getBytes());
		channel.close();
		conn.close();
	}

}
