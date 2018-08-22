package com.pm.portal.service.portal;

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
public class PortalRootClient {
	@Autowired
	private RestTemplate restTpl;

	
	
	
	
	//=============

	@HystrixCommand(fallbackMethod = "saveAgencyFallback", groupKey = "saveAgencyGroup", commandKey = "saveAgencyCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String saveAgency(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/save_agency", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String saveAgencyFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
    //===============
	
	@HystrixCommand(fallbackMethod = "getAgencysFallback", groupKey = "getAgencysGroup", commandKey = "getAgencysCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getAgencys(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/get_agencys", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String getAgencysFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	//===============
	
	@HystrixCommand(fallbackMethod = "saveRoleFallback", groupKey = "saveRoleGroup", commandKey = "saveRoleCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String saveRole(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/save_role", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String saveRoleFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	
	
	@HystrixCommand(fallbackMethod = "getRolesFallback", groupKey = "getRolesGroup", commandKey = "getRolesCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getRoles(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/get_roles", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String getRolesFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	
	//===============

	@HystrixCommand(fallbackMethod = "saveResFallback", groupKey = "saveResGroup", commandKey = "saveResCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String saveRes(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/save_res", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String saveResFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	
	
	@HystrixCommand(fallbackMethod = "getResesFallback", groupKey = "getResesGroup", commandKey = "getResesCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getReses(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/get_reses", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String getResesFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	
	
	//权限
	

	//角色
	
	@HystrixCommand(fallbackMethod = "getRoleResesFallback", groupKey = "getRoleResesGroup", commandKey = "getRoleResesCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getRoleReses(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/get_role_reses", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String getRoleResesFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}

	
	@HystrixCommand(fallbackMethod = "setRoleResesFallback", groupKey = "setRoleResesGroup", commandKey = "setAgencyRolesCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String setRoleReses(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/set_role_reses", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String setRoleResesFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}

	
	
	@HystrixCommand(fallbackMethod = "getDictGroupROOTFallback", groupKey = "getDictGroupROOTGroup", commandKey = "getDictGroupROOTCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getDictGroup(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/root/get_dict_group", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String getDictGroupROOTFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	
	
}
