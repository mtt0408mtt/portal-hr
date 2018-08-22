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
public class PortalOptClient {
	@Autowired
	private RestTemplate restTpl;

	
	
	
	@HystrixCommand(fallbackMethod = "getAdminsFallback", groupKey = "getAdminsGroup", commandKey = "getAdminsCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getAdmins(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/opt/get_admins", vars, String.class);

		System.out.println(result);

		return result;
	}

	public String getAdminsFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}


	
	//=============
	
	@HystrixCommand(fallbackMethod = "saveAdminFallback", groupKey = "saveAdminGroup", commandKey = "saveAdminCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String saveAdmin(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/opt/save_admin", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String saveAdminFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	

	
	
	//权限
	//用户
	
	@HystrixCommand(fallbackMethod = "getAgencyRolesFallback", groupKey = "getAgencyRolesGroup", commandKey = "getAgencyRolesCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getAgencyRoles(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/opt/get_agency_roles", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String getAgencyRolesFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}

	@HystrixCommand(fallbackMethod = "setAgencyRolesFallback", groupKey = "setAgencyRolesGroup", commandKey = "setAgencyRolesCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String setAgencyRoles(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/opt/set_agency_roles", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String setAgencyRolesFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}
	
	//dict 信息

	
	@HystrixCommand(fallbackMethod = "getDictGroupFallback", groupKey = "getDictGroupGroup", commandKey = "getDictGroupCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String getDictGroup(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/portal-service/portal/opt/get_dict_group", vars, String.class);

	    System.out.println(result);

		return result;
	}

	public String getDictGroupFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR));
	}


	
	
	
}
