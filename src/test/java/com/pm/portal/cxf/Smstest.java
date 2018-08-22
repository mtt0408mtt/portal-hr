package com.pm.portal.cxf;


import java.io.InputStream;
import java.text.MessageFormat;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

public class Smstest {

	public static void main(String[] args) throws Exception {
		// 创建WebClient
		String url=MessageFormat.format("https://sh2.ipyy.com/sms.aspx?action=send&userid=&account={0}&password={1}&mobile={2}&content={3}&sendTime=&extno=","sj456","sj456789","18862237663","【天瑞精准】您的验证码为："+CxfClient.createRandom(true,5));
		WebClient client = WebClient.create(url);
		// 获取响应
		Response response = client.get();
		// 获取响应内容
		InputStream ent = (InputStream) response.getEntity();
		String content = IOUtils.readStringFromStream(ent);
		// 输出字符串
		System.out.println(content);
		
		//System.out.println(CxfClient.createRandom(true,5));
	}
	
	
	
	 public static String createRandom(boolean numberFlag, int length){  
		  String retStr = "";  
		  String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";  
		  int len = strTable.length();  
		  boolean bDone = true;  
		  do {  
		   retStr = "";  
		   int count = 0;  
		   for (int i = 0; i < length; i++) {  
		    double dblR = Math.random() * len;  
		    int intR = (int) Math.floor(dblR);  
		    char c = strTable.charAt(intR);  
		    if (('0' <= c) && (c <= '9')) {  
		     count++;  
		    }  
		    retStr += strTable.charAt(intR);  
		   }  
		   if (count >= 2) {  
		    bDone = false;  
		   }  
		  } while (bDone);  
		  
		  return retStr;  
		 } 

}



