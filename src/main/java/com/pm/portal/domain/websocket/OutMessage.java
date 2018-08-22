package com.pm.portal.domain.websocket;

import java.util.Date;

import lombok.Data;
@Data
public class OutMessage {

	private String from;
	
	private String content;
	
	private Date time = new Date();

	public OutMessage(){}
	
	public OutMessage(String content){
		this.content = content;
		
	}
	
	

	
	
	
	
}
