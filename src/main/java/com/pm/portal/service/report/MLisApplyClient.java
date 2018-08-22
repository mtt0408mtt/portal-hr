package com.pm.portal.service.report;

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
public class MLisApplyClient {
	
	@Autowired
	private RestTemplate restTpl;

	@HystrixCommand(fallbackMethod = "mLisApplySaveFallback", groupKey = "mLisApplySaveGroup", commandKey = "mLisApplySaveCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String mLisApplySave(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://pm-zuul-gateway/report-lis-service/mlis/apply_save",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String mLisApplySaveFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.MLIS_SERVER_ERROR));
	}
	
	
	@HystrixCommand(fallbackMethod = "getZuheFallback", groupKey = "getZuheGroup", commandKey = "getZuheCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getZuhe(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://pm-zuul-gateway/report-lis-service/mlis/get_zuhe",vars, String.class);
		
		System.out.println(result);
		return result;
	}
	
	public String getZuheFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.MLIS_SERVER_ERROR));
	}

}
