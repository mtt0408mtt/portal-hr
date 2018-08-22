package com.pm.portal.controller.sjhc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.report.ReportLisClient;
import com.pm.portal.service.sjhc.SjhcReportClient;

@RestController
@RequestMapping(value = "/report_sjhc_c")
public class ReportSjhcConsumerController {
	
	
	@Autowired
	private SjhcReportClient sjhcReportClient;
	
	@RequestMapping(value = "/kchw_aggr_report", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object tmxxAggrReport(HttpServletResponse response, HttpServletRequest request) {
		int indexGroup = 0;
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			if (null != request.getParameter("year")) {
				String year = request.getParameter("year");
				if (!StringUtils.isEmpty(year)) {
					vars.put("year", Arrays.asList(year.split(",")));
				}
			}else{
				return Result.error(CodeMsg.SJHC_REPORT_QUERY_PARAMS_NULL);
			}

			if (null != request.getParameter("month")) {
				String month = request.getParameter("month");
				if (!StringUtils.isEmpty(month)) {
					vars.put("month", Arrays.asList(month.split(",")));
				}
			}else{
				return Result.error(CodeMsg.SJHC_REPORT_QUERY_PARAMS_NULL);
			}

			if (null != request.getParameter("wpbh")) {
				Boolean wpbh = Boolean.valueOf(request.getParameter("wpbh"));
				if (!StringUtils.isEmpty(wpbh) && wpbh) {
					vars.put("wpbh", wpbh);
					indexGroup++;
				}
			}

			if (null != request.getParameter("wpph")) {
				Boolean wpph = Boolean.valueOf(request.getParameter("wpph"));
				if (!StringUtils.isEmpty(wpph) && wpph) {
					vars.put("wpph", wpph);
					indexGroup++;
				}
			}

			if (null != request.getParameter("hwbm")) {
				Boolean hwbm = Boolean.valueOf(request.getParameter("hwbm"));
				if (!StringUtils.isEmpty(hwbm) && hwbm) {
					vars.put("hwbm", hwbm);
					indexGroup++;
				}
			}

			if (null != request.getParameter("ckbh")) {
				Boolean ckbh = Boolean.valueOf(request.getParameter("ckbh"));
				if (!StringUtils.isEmpty(ckbh) && ckbh) {
					vars.put("ckbh", ckbh);
					indexGroup++;
				}
			}

			if (indexGroup < 1) {
				return Result.error(CodeMsg.SJHC_REPORT_QUERY_PARAMS_NULL);
			}
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return sjhcReportClient.kchwAggrReport(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

}
