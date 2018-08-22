package com.pm.portal.controller.report;

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
import com.pm.portal.service.report.MLisApplyClient;

@RestController
@RequestMapping(value = "/m_lis_c")
public class MLisApplyController {

	@Autowired
	private MLisApplyClient mLisApplyClient;

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object save(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			if (null != request.getParameter("code") && null != request.getParameter("cname")
					&& null != request.getParameter("cnum") && null != request.getParameter("sex")
					&& null != request.getParameter("age") && null != request.getParameter("zhid")
					&& null != request.getParameter("jgid") && null != request.getParameter("wltm")
					&& null != request.getParameter("birthday")) {
				setParams(request, vars, "code");
				setParams(request, vars, "cname");
				setParams(request, vars, "cnum");
				setParams(request, vars, "sex");
				setParams(request, vars, "age");
				setParams(request, vars, "zhid");
				setParams(request, vars, "jgid");
				setParams(request, vars, "wltm");
				setParams(request, vars, "birthday");

			} else {
				return Result.error(CodeMsg.MLIS_PARAMS_ERROR);
			}

			return mLisApplyClient.mLisApplySave(vars);

		} catch (
		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	private void setParams(HttpServletRequest request, Map<String, Object> vars, String p_name) {
		String name = request.getParameter(p_name);
		if (!StringUtils.isEmpty(name)) {
			vars.put(p_name, name);
		}
	}

	@RequestMapping(value = "/get/zuhe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getZuhe(HttpServletResponse response, HttpServletRequest request) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			if (null != request.getParameter("jgid")) {
				setParams(request, vars, "jgid");
			} else {
				return Result.error(CodeMsg.MLIS_PARAMS_ERROR);
			}
			return mLisApplyClient.getZuhe(vars);

		} catch (
		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

}
