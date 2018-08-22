package com.pm.portal.controller.lis;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.domain.websocket.InMessage;
import com.pm.portal.service.base.WebSocketService;

@RestController
public class WebSocketController {
	@Autowired
	private WebSocketService ws;

	
//	@MessageMapping("/apply/chat")
//	@SendTo("/topic/apply_chat")
//	public String applyInfo(String message){
//		System.out.println("获得一条消息");
//		return message;
//	}
	
	
	@MessageMapping("/apply/chat")
	public void gameInfo(String msg) throws InterruptedException{
		InMessage message=new InMessage();
		message.setContent(msg);
		ws.sendChatMessage(message);
		ws.sendTopicMessage("/topic/apply_chat",message);
	}
	
	//点对多
	@RequestMapping(value = "/apply/multi/send_message", method = RequestMethod.POST)
	public void sendMessage(HttpServletResponse response, HttpServletRequest request) throws InterruptedException {
		InMessage message=new InMessage();
		message.setContent(String.valueOf(request.getParameter("msg")));
		ws.sendTopicMessage("/topic/apply_chat",message);
		
	}
	//点对点
	@RequestMapping(value = "/apply/single/send_message", method = RequestMethod.POST)
	public void singleChat(InMessage message) {
         System.out.println(message.toString()); //InMessage(from=null, to=null, content=12121, time=null)
         ws.sendChatMessage(message);
		
	}
	
	//定时推送
//	@MessageMapping("/schedule/push")
//	@Scheduled(fixedRate = 3000)  //方法不能加参数 
//	public void sendServerInfo(){
//		System.out.println(new Date().toLocaleString());
//		ws.sendServerInfo();
//	}
	
}
