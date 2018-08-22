package com.pm.portal.controller.lis;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.lis.LisDicClient;

@RestController
@RequestMapping(value = "/lis_c/dic")
public class DicConsumerController {

	@Autowired
	private LisDicClient client;

	// @RequestMapping(value = "/getDicSelect", method = RequestMethod.POST,
	// produces = MediaType.APPLICATION_JSON_VALUE)
	// public Object getDicSelect(HttpServletResponse response,
	// HttpServletRequest request) {
	// Map<String, Object> vars = new HashMap<String, Object>();
	// try {
	//
	// if (null != request.getParameter("dic_code") &&
	// !StringUtils.isEmpty("dic_code")){
	// String dic_code = String.valueOf(request.getParameter("dic_code"));
	// vars.put("dic_code", dic_code);
	//
	// }else{
	// return Result.error(CodeMsg.LIS_PARAMS_ERROR);
	// }
	// return client.getDicSelect(vars);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// return Result.error(CodeMsg.LIS_DIC_ERROR);
	// }
	// }

	@RequestMapping(value = "/get_dic_sendUnit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getDicSendUnit(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("agency")&& null != request.getParameter("group")) {
				String agency = String.valueOf(request.getParameter("agency"));
				String group = String.valueOf(request.getParameter("group"));
				agency = agency.substring(agency.length() - 2);
				vars.put("agency", agency);
				vars.put("group", group);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			return client.getDicSendUnit(vars);

		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_DIC_ERROR);
		}
	}

	@RequestMapping(value = "/get_patient_type", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getPatientType(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("dmlb")) {
				vars.put("dmlb", request.getParameter("dmlb"));
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			return client.getDicPatientType(vars);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_DIC_ERROR);
		}
	}

	@RequestMapping(value = "/get_apply_type", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getApplyType(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("dmlb")) {
				vars.put("dmlb", request.getParameter("dmlb"));
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			return client.getDicApplyType(vars);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_DIC_ERROR);
		}
	}

	@RequestMapping(value = "/get_dic_dmlb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getDicByDmlb(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("dmlb")){
				vars.put("dmlb", request.getParameter("dmlb"));
			}else{
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			return client.getDicByDmlb(vars);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_DIC_ERROR);
		}
	}
	
	@RequestMapping(value = "/get_sample_type", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getSampleType(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		
		try {
			vars.put("admin", admin);
			return client.getSampleType(vars);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_DIC_ERROR);
		}
	}
	
	
	
	@RequestMapping(value = "/get_combination", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCombination(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			Integer pageIndex = 1;
			Integer pageSize = 20;
			if (request.getParameter("pageIndex") != null && request.getParameter("pageSize") != null) {
				pageIndex = Integer.parseInt((String) request.getParameter("pageIndex"));
				pageSize = Integer.parseInt((String) request.getParameter("pageSize"));
			}
			vars.put("pageIndex", pageIndex);
			vars.put("pageSize", pageSize);
			int startIndex=(pageIndex - 1) * pageSize;
			vars.put("startIndex", startIndex);
			
//			if (null != request.getParameter("agency") && null != request.getParameter("group")){
//				vars.put("agency", request.getParameter("agency"));
//				vars.put("group", request.getParameter("group"));
//			}else{
//				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
//			}
	
			if (null != request.getParameter("comb_category_id")  ){
				String comb_category_id=request.getParameter("comb_category_id");
				if(!StringUtils.isEmpty(comb_category_id)){
					vars.put("comb_category_id", comb_category_id);
				}

			}else{
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}			

			if (null != request.getParameter("condition")  ){ //&& null != request.getParameter("combSearchName")
				String combSearchName=request.getParameter("combSearchName");
				if(!StringUtils.isEmpty(combSearchName)){
					vars.put("condition", request.getParameter("condition"));
					vars.put("combSearchName", combSearchName.toString().toUpperCase());
				}

			}else{
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			return client.getCombination(vars);

		} catch (	

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
	
	@RequestMapping(value = "/get_comb_category", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCombCategory(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			return client.getCombCategory(vars);

		} catch (	

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/get_instrument", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getInstrument(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			return client.getInstrument(vars);

		} catch (	

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
	

	
	

}
