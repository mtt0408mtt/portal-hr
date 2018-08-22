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
import com.pm.portal.service.lis.LisOptClient;

@RestController
@RequestMapping(value = "/lis_c/opt")
public class OptConsumerController {
	
	@Autowired
	private LisOptClient client;
	
	
	
//	ybh_prefix:$scope.hb.headDate.Format("YYYYMMDD")+code,
//	instrument_id:$scope.hb.dic_instrument.split(",")[0]
	
	@RequestMapping(value = "/select_instrument", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object selectInstrument(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);


			
			if (null != request.getParameter("ybh_prefix")) {
				String ybh_prefix = String.valueOf(request.getParameter("ybh_prefix"));
				System.out.println("ybh_prefix=,"+ybh_prefix);
				vars.put("ybh_prefix", ybh_prefix);
				
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("instrument_id")) {
				String instrument_id = request.getParameter("instrument_id");
                vars.put("instrument_id", instrument_id);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			return client.selectInstrument(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
	
	//getobjbyybh

	
	@RequestMapping(value = "/getobjbyybh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getobjbyybh(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);

			
			if (null != request.getParameter("ybh_prefix") && null != request.getParameter("ybh_seq")) {
				String ybh_prefix = String.valueOf(request.getParameter("ybh_prefix"));
				String ybh_seq = String.valueOf(request.getParameter("ybh_seq"));
				System.out.println("ybh_prefix=,"+ybh_prefix);
				vars.put("ybh_seq", ybh_prefix+ybh_seq);
				vars.put("ybh_prefix", ybh_prefix);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("instrument_id")) {
				String instrument_id = request.getParameter("instrument_id");
                vars.put("instrument_id", instrument_id);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			

			return client.getobjbyybh(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
	
	

	
	@RequestMapping(value = "/update_obj", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object updateObj(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("ybh_prefix") && null != request.getParameter("ybh_seq")) {
				String ybh_prefix = String.valueOf(request.getParameter("ybh_prefix"));
				String ybh_seq = String.valueOf(request.getParameter("ybh_seq"));
				System.out.println("ybh_prefix=,"+ybh_prefix);
				vars.put("ybh_seq", ybh_prefix+ybh_seq);
				vars.put("ybh_prefix", ybh_prefix);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("instrument_id")) {
				String instrument_id = request.getParameter("instrument_id");
                vars.put("instrument_id", instrument_id);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}


			
			if (null != request.getParameter("age") && null != request.getParameter("nldw")) {
				String age = String.valueOf(request.getParameter("age"));
			
				String nldw = String.valueOf(request.getParameter("nldw"));
				if (!StringUtils.isEmpty(age) && !StringUtils.isEmpty(nldw)) {
					vars.put("age", age);
					vars.put("nldw", nldw);
					vars.put("ageStr", age + nldw);
				}
			}else{
				vars.put("age", null);
				vars.put("ageStr", null);
				vars.put("nldw", null);
			}
			if (null != request.getParameter("yepb")) {
				String yepb = String.valueOf(request.getParameter("yepb"));
				System.out.println("yepb=,"+yepb);
				if (!yepb.equals("true")) {

					vars.put("yepb", 0);
				} else {
					vars.put("yepb", 1);
				}
			} else {
				vars.put("yepb", 0);
			}
			
			
			
			if (null != request.getParameter("patient_type")) {
				String patient_type = String.valueOf(request.getParameter("patient_type"));
				vars.put("patient_type_id", patient_type);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			if (null != request.getParameter("sample_status")) {
				String sample_status = String.valueOf(request.getParameter("sample_status"));
				vars.put("sample_status_id", sample_status);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			if (null != request.getParameter("sample_type")) {
				String sample_type = String.valueOf(request.getParameter("sample_type"));
				vars.put("sample_type_id", sample_type);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			setParams(request, vars, "mzhm");
			setParams(request, vars, "brxm");
			setParams(request, vars, "sex");
			setParams(request, vars, "age");
			setParams(request, vars, "nldw");
			setParams(request, vars, "sqks");
			setParams(request, vars, "bedNo");	
			setParams(request, vars, "ynzd");
			setParams(request, vars, "bz");
			return client.updateObj(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}	
	private void setParams(HttpServletRequest request, Map<String, Object> vars, String p_name) {
		String name = request.getParameter(p_name);
		if (!StringUtils.isEmpty(name) && !name.equals("null")) {
			vars.put(p_name, name);
		}else{
			vars.put(p_name, null);
		}
	}
}
