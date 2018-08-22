package com.pm.portal.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	

	//子字符串modelStr在字符串str中第count次出现时的下标
	private static int getFromIndex(String str, String modelStr, Integer count) {
		//对子字符串进行匹配
	        Matcher slashMatcher = Pattern.compile(modelStr).matcher(str);
		int index = 0;
	        //matcher.find();尝试查找与该模式匹配的输入序列的下一个子序列
	       while(slashMatcher.find()) {
		    index++;
		    //当modelStr字符第count次出现的位置
		    if(index == count){
		       break;
		    }
		}
	        //matcher.start();返回以前匹配的初始索引。
	       return slashMatcher.start();
	}
	
	public static void main(String[] args) {
		String url="http://maxt:9022/portal-service/portal/opt/get_admins";
		int i=getFromIndex(url,"/",4);
		url=url.substring(i);
	    String pattern = "/portal/opt/.*";
	    boolean isMatch = Pattern.matches(pattern, url);
	    System.out.println(isMatch);
		
	}
}
