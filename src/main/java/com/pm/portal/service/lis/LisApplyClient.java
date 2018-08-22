package com.pm.portal.service.lis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;

@Service
//@DefaultProperties(groupKey = "lisApplyAddGroup") //全局
public class LisApplyClient {
	@Autowired
	private RestTemplate restTpl;
	
	
	
	@HystrixCommand(fallbackMethod = "lisApplyAddFallback", groupKey = "lisApplyAddGroup", commandKey = "lisApplyAddCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String lisApplyAdd(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/lis_apply_add",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String lisApplyAddFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//########################################
	
	@HystrixCommand(fallbackMethod = "lisApplySaveFallback", groupKey = "lisApplySaveGroup", commandKey = "lisApplySaveCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String lisApplySave(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://pm-zuul-gateway/lis-service/lis/apply/lis_apply_save",vars, String.class);
		
		System.out.println(result);
		
		
		return result;
	}

	public String lisApplySaveFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	//########################################
	
	@HystrixCommand(fallbackMethod = "lisApplyCheckFallback", groupKey = "lisApplyCheckReportGroup", commandKey = "lisApplyCheckCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String lisApplyCheck(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/lis_apply_check",vars, String.class);
		System.out.println(result);
		return result;
	}
	
	
	public String lisApplyCheckFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	
	//########################################
	
	@HystrixCommand(fallbackMethod = "lisApplyCancelApplyFallback", groupKey = "lisApplyCancelApplyGroup", commandKey = "lisApplyCancelApplyCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String lisApplyCancelApply(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/lis_apply_cancelapply",vars, String.class);
		System.out.println(result);
		return result;
	}
	
	
	public String lisApplyCancelApplyFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//########################################
	
	@HystrixCommand(fallbackMethod = "lisApplyDoneFallback", groupKey = "lisApplyDoneGroup", commandKey = "lisApplyDoneCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String lisApplyDone(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/lis_apply_done",vars, String.class);
		System.out.println(result);
		return result;
	}
	
	
	public String lisApplyDoneFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//########################################
	
	@HystrixCommand(fallbackMethod = "lisApplyDoneDocFallback", groupKey = "lisApplyDoneDocGroup", commandKey = "lisApplyDoneDocCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String lisApplyDoneDoc(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/lis_apply_donedoc",vars, String.class);
		System.out.println(result);
		return result;
	}
	
	
	public String lisApplyDoneDocFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//########################################
	
	@HystrixCommand(fallbackMethod = "queryApplyFallback", groupKey = "queryApplyGroup", commandKey = "queryApplyCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String queryApply(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/query_apply",vars, String.class);
		System.out.println(result);
		return result;
	}
	
	
	public String queryApplyFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	//########################################
	
	@HystrixCommand(fallbackMethod = "lisApplyUpdateStatusFallback", groupKey = "lisApplyUpdateStatusGroup", commandKey = "lisApplyUpdateStatusCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String lisApplyUpdateStatus(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/lis_apply_update_status",vars, String.class);
		System.out.println(result);
		return result;
	}
	
	
	public String lisApplyUpdateStatusFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	//########################################

	@HystrixCommand(fallbackMethod = "queryApply2Fallback", groupKey = "queryApply2Group", commandKey = "queryApply2CommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String queryApply2(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/query_apply2",vars, String.class);
		System.out.println(result);
		return result;
	}
	
	
	public String queryApply2Fallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	//===
	
	@HystrixCommand(fallbackMethod = "batchProcessFallback", groupKey = "batchProcessGroup", commandKey = "batchProcessCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String batchProcess(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		String result=restTpl.postForObject(
				"http://lis-service/lis/apply/batch_process",vars, String.class);
		System.out.println(result);
		return result;
	}
	

	public String batchProcessFallback(Map<String, Object> vars) {
		// TODO Auto-generated method stub
		return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
	}
	
	
	

	
	//


	
	
	
}
