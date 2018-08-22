package com.pm.portal.service.sjhc;

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
public class SjhcReportClient {
	@Autowired
	private RestTemplate restTpl;
	
	
	@HystrixCommand(fallbackMethod = "kchwAggrReportFallback", groupKey = "KchwAggrReportGroup", commandKey = "KchwAggrReportCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String kchwAggrReport(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/kchw_aggr_report", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String kchwAggrReportFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.SJHC_REPORT_SERVER_ERROR));
	}


	
	//=========
	
	
}
