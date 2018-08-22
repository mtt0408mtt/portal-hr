package com.pm.portal.service.lis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;

@Service
public class LisOptClient {
	@Autowired
	private RestTemplate restTpl;
	
	
	
	@HystrixCommand(fallbackMethod = "selectInstrumentFallback", groupKey = "selectInstrumentGroup", commandKey = "selectInstrumentCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String selectInstrument(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/opt/select_instrument",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String selectInstrumentFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}

	
	//====
	@HystrixCommand(fallbackMethod = "getobjbyybhFallback", groupKey = "getobjbyybhGroup", commandKey = "getobjbyybhCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getobjbyybh(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/opt/getobjbyybh",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getobjbyybhFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//==


	
	
	@HystrixCommand(fallbackMethod = "updateObjFallback", groupKey = "updateObjGroup", commandKey = "updateObjCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String updateObj(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/opt/update_obj",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String updateObjFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}

}
