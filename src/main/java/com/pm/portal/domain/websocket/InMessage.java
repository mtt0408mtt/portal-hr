package com.pm.portal.domain.websocket;

import java.util.Date;

import lombok.Data;
@Data
public class InMessage {
	
	private String from;
	
	private String to;
	
	private String content;
	
	private Date time;

	
	


}
