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
public class ReportLisClient {

	@Autowired
	private RestTemplate restTpl;

	@HystrixCommand(fallbackMethod = "tmxxAggrReportFallback", groupKey = "tmxxAggrReportGroup", commandKey = "tmxxAggrReportCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String tmxxAggrReport(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/tmxx_aggr_report", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String tmxxAggrReportFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_SERVER_ERROR));
	}
	
	//=========

	@HystrixCommand(fallbackMethod = "tmxxExportExcelFallback", groupKey = "tmxxExportExcelReportGroup", commandKey = "tmxxExportExcelCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String tmxxExportExcel(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/tmxx_export_excel", vars, String.class);
		//System.out.println(result);
		return result;
	}

	public String tmxxExportExcelFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_EXPORT_ERROR));
	}

	// =========
	
	@HystrixCommand(fallbackMethod = "tmxxAggrExportExcelFallback", groupKey = "tmxxAggrExportExcelReportGroup", commandKey = "tmxxAggrExportExcelCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String tmxxAggrExportExcel(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/tmxx_aggr_export_excel", vars, String.class);
		//System.out.println(result);
		return result;
	}

	public String tmxxAggrExportExcelFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_EXPORT_ERROR));
	}

	// =========

	@HystrixCommand(fallbackMethod = "tmmxAggrReportFallback", groupKey = "tmmxAggrReportGroup", commandKey = "tmmxAggrReportCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String tmmxAggrReport(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/tmmx_aggr_report", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String tmmxAggrReportFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_SERVER_ERROR));
	}

	@HystrixCommand(fallbackMethod = "tmmxExportExcelFallback", groupKey = "tmmxExportExcelReportGroup", commandKey = "tmmxExportExcelCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String tmmxExportExcel(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/tmmx_export_excel", vars, String.class);
		//System.out.println(result);
		return result;
	}

	public String tmmxExportExcelFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_EXPORT_ERROR));
	}

	// =========

	@HystrixCommand(fallbackMethod = "itemAggrReportFallback", groupKey = "itemAggrReportGroup", commandKey = "itemAggrReportCommandKey", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String itemAggrReport(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/item_aggr_report", vars, String.class);

		//System.out.println(result);

		return result;
	}

	public String itemAggrReportFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_SERVER_ERROR));
	}
	//=========

	@HystrixCommand(fallbackMethod = "itemExportExcelFallback", groupKey = "itemExportExcelReportGroup", commandKey = "itemExportExcel", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String itemExportExcel(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/item_export_excel", vars, String.class);
		//System.out.println(result);
		return result;
	}

	public String itemExportExcelFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_EXPORT_ERROR));
	}
	
	//=========
	
	@HystrixCommand(fallbackMethod = "tmmxAggrExportExcelFallback", groupKey = "tmmxAggrExportExportExcelReportGroup", commandKey = "tmmxAggrExportExportExcel", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String tmmxAggrExportExcel(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/tmmx_aggr_export_excel", vars, String.class);
		//System.out.println(result);
		return result;
	}

	
	
	public String tmmxAggrExportExcelFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_EXPORT_ERROR));
	}
	
	//=========

	
	@HystrixCommand(fallbackMethod = "itemAggrExportExcelFallback", groupKey = "itemAggrExportExcelReportGroup", commandKey = "itemAggrExportExportExcel", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100000") }, threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value = "5") })
	public String itemAggrExportExcel(Map<String, Object> vars) {
		for (Map.Entry<String, Object> entry : vars.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		String result = restTpl.postForObject("http://pm-zuul-gateway/report-lis-service/report_lis/item_aggr_export_excel", vars, String.class);
		//System.out.println(result);
		return result;
	}

	
	
	public String itemAggrExportExcelFallback(Map<String, Object> vars) {
		return JSONObject.toJSONString(Result.error(CodeMsg.REPROT_LIS_EXPORT_ERROR));
	}


}
