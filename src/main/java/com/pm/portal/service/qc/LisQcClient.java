package com.pm.portal.service.qc;

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
public class LisQcClient {
	@Autowired
	private RestTemplate restTpl;
	
	
	
//	@HystrixCommand(fallbackMethod = "getDicSelectFallback", groupKey = "getDicSelectGroup", commandKey = "getDicSelectCommandKey", commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
//					@HystrixProperty(name = "coreSize", value = "5") })
//	public String getDicSelect(Map<String, Object> vars) {
//		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
//		}  
//		String result=restTpl.postForObject(
//				"http://qc/dic/get_dic_select",vars, String.class);
//		
//		System.out.println(result);
//		
//		
//		return result;
//	}
//
//	public String getDicSelectFallback(Map<String, Object> vars) {
//		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
//	}
	
	//======================
	
	@HystrixCommand(fallbackMethod = "getDicSendUnitFallback", groupKey = "getDicSendUnitGroup", commandKey = "getDicSendUnitCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getDicSendUnit(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_dic_sendUnit",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getDicSendUnitFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//======================
	
	
	
	@HystrixCommand(fallbackMethod = "getPatientTypeFallback", groupKey = "getPatientTypeGroup", commandKey = "getPatientTypeCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getDicPatientType(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_patient_type",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getPatientTypeFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	//======================
	
	
	@HystrixCommand(fallbackMethod = "getApplyTypeFallback", groupKey = "getApplyTypeGroup", commandKey = "getApplyTypeCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getDicApplyType(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_apply_type",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getApplyTypeFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//======================
	
	@HystrixCommand(fallbackMethod = "getDicByDmlbFallback", groupKey = "getDicByDmlbGroup", commandKey = "getDicByDmlbCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getDicByDmlb(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_dic_dmlb",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getDicByDmlbFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//======================
	
	@HystrixCommand(fallbackMethod = "getSampleTypeFallback", groupKey = "getSampleTypeGroup", commandKey = "getSampleTypeCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getSampleType(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_sample_type",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getSampleTypeFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//======================
	
	@HystrixCommand(fallbackMethod = "getCombinationFallback", groupKey = "getCombinationGroup", commandKey = "getCombinationCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getCombination(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_combination",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getCombinationFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	//======================
	@HystrixCommand(fallbackMethod = "getCombCategoryFallback", groupKey = "getCombCategoryGroup", commandKey = "getCombCategoryCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getCombCategory(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_comb_category",vars, String.class);
		
		System.out.println(result);
		
		return result;
	}

	public String getCombCategoryFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	
	
	@HystrixCommand(fallbackMethod = "getYqbhFallback", groupKey = "getYqbhGroup", commandKey = "getYqbhCommandKey", commandProperties = {
	@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
	@HystrixProperty(name = "coreSize", value = "5") })
	public String getYqbh(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/get_yqbh",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}
	
	public String getZhlb(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://qc/dic/getZhlb",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String getYqbhFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	





	
	
	
	
	
	
	
}
