package com.pm.portal.controller.lis;

import java.text.SimpleDateFormat;
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

import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.redis.RedisService;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.lis.LisApplyClient;
import com.pm.portal.util.PMUtil;

@RestController
@RequestMapping(value = "/lis_c/apply")
public class ApplyConsumerController {

	@Autowired
	private LisApplyClient client;
	
	@Autowired
	private RedisService redisService;

	// send_unit:$scope.apply.send_unit, //Y
	// wltm:$scope.apply.wltm,
	// yntm:$scope.apply.yntm,
	// patient_type:$scope.apply.patient_type,//Y
	// yepb:$scope.apply.yepb,
	// mzhm:$scope.apply.mzhm,
	// brid:$scope.apply.brid,
	// brxm:$scope.apply.brxm,//Y
	// sfid:$scope.apply.sfid,
	// sex_type:$scope.apply.sex_type,
	// age:$scope.apply.age,
	// age_unit:$scope.apply.age_unit,
	// csrq:$scope.apply.csrq,
	// apply_type:$scope.apply.apply_type,//Y
	// sample_type:$scope.apply.sample_type,//Y
	// sample_status:$scope.apply.sample_status,//Y
	// sqks:$scope.apply.sqks,
	// sqbq:$scope.apply.sqbq,
	// bedNo:$scope.apply.bedNo,
	// sqys:$scope.apply.sqys,
	// ynzd:$scope.apply.ynzd,
	// sqsj:$scope.apply.sqsj,
	// comment:$scope.apply.comment,

	// zcombs:$scope.apply.zcombs

	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object lisApplyAdd(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("send_unit")) {
				String send_unit = String.valueOf(request.getParameter("send_unit"));
				vars.put("send_unit_id", send_unit.split(",")[0]);
				vars.put("send_unit_name", send_unit.split(",")[1]);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			if (null != request.getParameter("sqid")) {
				String sqid = String.valueOf(request.getParameter("sqid"));
				System.out.println("sqid," + sqid);
				if (!StringUtils.isEmpty(sqid)) {
					vars.put("sqid", sqid);
				}
			}

			if (null != request.getParameter("patient_type")) {
				String patient_type = String.valueOf(request.getParameter("patient_type"));
				vars.put("patient_type_id", patient_type);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			if (null != request.getParameter("apply_type")) {
				String apply_type = String.valueOf(request.getParameter("apply_type"));
				vars.put("apply_type_id", apply_type);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			if (null != request.getParameter("sample_type")) {
				String sample_type = String.valueOf(request.getParameter("sample_type"));
				vars.put("sample_type_id", sample_type);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			if (null != request.getParameter("sample_status")) {
				String sample_status = String.valueOf(request.getParameter("sample_status"));
				vars.put("sample_status_id", sample_status);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			if (null != request.getParameter("brxm")) {
				String brxm = request.getParameter("brxm");
				if (!StringUtils.isEmpty(brxm)) {
					vars.put("brxm", brxm);
				} else {
					return Result.error(CodeMsg.LIS_PARAMS_ERROR);
				}
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			// csrq
			// ageStr
			// sqsj
			// yepb

			if (null != request.getParameter("csrq")) {
				String csrq = String.valueOf(request.getParameter("csrq"));
				if (!StringUtils.isEmpty(csrq)) {
					vars.put("csrq", PMUtil.UTCStringtODefaultStringDay(csrq));
				}
			}
			if (null != request.getParameter("sqsj")) {
				String sqsj = String.valueOf(request.getParameter("sqsj"));
				if (!StringUtils.isEmpty(sqsj)) {
					vars.put("sqsj", PMUtil.UTCStringtODefaultString(sqsj));
				}
			}

			if (null != request.getParameter("age") && null != request.getParameter("age_unit")) {
				String age = String.valueOf(request.getParameter("age"));
			
				String age_unit = String.valueOf(request.getParameter("age_unit"));
				if (!StringUtils.isEmpty(age) && !StringUtils.isEmpty(age_unit)) {
					vars.put("age", age);
					vars.put("age_unit", age_unit);
					vars.put("ageStr", age + age_unit);
				}
			}else{
				vars.put("age", null);
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

			setParams(request, vars, "cellphone");
			setParams(request, vars, "wltm");
			setParams(request, vars, "yntm");
			setParams(request, vars, "mzhm");
			setParams(request, vars, "brid");
			setParams(request, vars, "sfid");
			setParams(request, vars, "sex_type");
			setParams(request, vars, "sqks");
			setParams(request, vars, "sqbq");
			setParams(request, vars, "bedNo");
			setParams(request, vars, "sqys");
			setParams(request, vars, "ynzd");
			setParams(request, vars, "comment");

			if (null != request.getParameter("zcombs")) {
				String[] zcombs = request.getParameterValues("zcombs");
				vars.put("zcombs", zcombs);
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			return client.lisApplyAdd(vars);

		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/update_status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object lisApplyUpdateStatus(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("sqid")) {
				String sqid = String.valueOf(request.getParameter("sqid"));
				if (!StringUtils.isEmpty(sqid)) {
					vars.put("sqid", sqid);
				}
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			
			if (null != request.getParameter("autodone")) {
				String autodone = String.valueOf(request.getParameter("autodone"));
				if (!autodone.equals("true")) {

					vars.put("autodone", "false");
				} else {
					vars.put("autodone", "true");
				}
			} else {
				vars.put("autodone", "false");
			}
			
			if (null != request.getParameter("splitRule")) {
				String splitRule = String.valueOf(request.getParameter("splitRule"));
				System.out.println("splitRule=,"+splitRule);
				if (!splitRule.equals("true")) {

					vars.put("splitRule", "false");
				} else {
					vars.put("splitRule", "true");
				}
			} else {
				vars.put("splitRule", "false");
			}
			

			if (null != request.getParameter("sqdzt")) {
				String sqdzt = String.valueOf(request.getParameter("sqdzt"));
				if (!StringUtils.isEmpty(sqdzt)) {
					vars.put("sqdzt", sqdzt);
				}
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			String result = client.lisApplyUpdateStatus(vars);

			return result;

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object lisApplyCheck(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {

			return null;

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/cancel_check", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object lisApplyCancelCheck(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {

			return null;

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/cancel_apply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object lisApplyCancelApply(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {

			return null;

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/done", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object lisApplyDone(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {

			return null;

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/cancel_done", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object lisApplyDoneDoc(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {

			return null;

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/query_apply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object queryApply(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			if (null != request.getParameter("strDStart") && null != request.getParameter("strDEnd")) {
				String strDStart = String.valueOf(request.getParameter("strDStart"));
				String strDEnd = String.valueOf(request.getParameter("strDEnd"));
				
				if (!StringUtils.isEmpty(strDStart) && !StringUtils.isEmpty(strDEnd)) {
					vars.put("strDStart", PMUtil.UTCStringtODefaultStringDay(strDStart));
					vars.put("strDEnd", PMUtil.UTCStringtODefaultStringDay(strDEnd));
				} else {
					Result.error(CodeMsg.LIS_PARAMS_ERROR);
				}
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			
//			if (null != request.getParameter("condition")) {
//				String conditionValue = request.getParameter("conditionValue");
//				if (!StringUtils.isEmpty(conditionValue)) {
//					vars.put("condition", request.getParameter("condition"));
//					vars.put("conditionValue", conditionValue.toString().toUpperCase());
//				}
//			}


			if (null != request.getParameter("sqdzt") ) {
				String sqdzt = String.valueOf(request.getParameter("sqdzt"));
				if(!StringUtils.isEmpty(sqdzt)){
					vars.put("sqdzt", sqdzt);
				}
			} else {
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			
			
			if (null != request.getParameter("sjdw")) {

				String sjdw = String.valueOf(request.getParameter("sjdw"));
				if (!StringUtils.isEmpty(sjdw)) {
					vars.put("sjdw", sjdw.split(",")[0]);
				}

			}


			return client.queryApply(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}

	@RequestMapping(value = "/query_apply2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object queryApply2(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
//			if (null != request.getParameter("strDStart") && null != request.getParameter("strDEnd")) {
//				String strDStart = String.valueOf(request.getParameter("strDStart"));
//				String strDEnd = String.valueOf(request.getParameter("strDEnd"));
//				
//				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//				f1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//				
//				
//				if (!StringUtils.isEmpty(strDStart) && !StringUtils.isEmpty(strDEnd)) {
//					vars.put("strDStart",  PMUtil.UTCStringtODefaultStringDay(strDStart));
//					vars.put("strDEnd",  PMUtil.UTCStringtODefaultStringDay(strDEnd));
//				} else {
//					Result.error(CodeMsg.LIS_PARAMS_ERROR);
//				}
//			} else {
//				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
//			}
			
			
			if (null != request.getParameter("condition")) {
				String conditionValue = request.getParameter("conditionValue");
				if (!StringUtils.isEmpty(conditionValue)) {
					vars.put("condition", request.getParameter("condition"));
					vars.put("conditionValue", conditionValue.toString().toUpperCase());
				}
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			
//			if (null != request.getParameter("sqdzt") ) {
//				String sqdzt = String.valueOf(request.getParameter("sqdzt"));
//				if(!StringUtils.isEmpty(sqdzt)){
//					vars.put("sqdzt", sqdzt);
//				}
//			} else {
//				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
//			}
//			
//			
//			if (null != request.getParameter("sjdw")) {
//
//				String sjdw = String.valueOf(request.getParameter("sjdw"));
//				if (!StringUtils.isEmpty(sjdw)) {
//					vars.put("sjdw", sjdw.split(",")[0]);
//				}
//
//			}

			return client.queryApply2(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
	
	
	//batch_process
	
	@RequestMapping(value = "/batch_process", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object batchProcess(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);

			if (null != request.getParameterValues("ids")) {
				String[] ids = request.getParameterValues("ids");
                vars.put("ids", ids);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("splitRule")) {
				String splitRule = String.valueOf(request.getParameter("splitRule"));
				System.out.println("splitRule=,"+splitRule);
				if (!splitRule.equals("true")) {

					vars.put("splitRule", "false");
				} else {
					vars.put("splitRule", "true");
				}
			} else {
				vars.put("splitRule", "false");
			}
			
			if (null != request.getParameter("sqdzt")) {
				String sqdzt = request.getParameter("sqdzt");
                vars.put("sqdzt", sqdzt);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			return client.batchProcess(vars);

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
		}
	}

}
