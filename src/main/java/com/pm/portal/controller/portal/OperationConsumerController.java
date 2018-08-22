package com.pm.portal.controller.portal;

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

import com.pm.portal.access.AccessLimit;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.portal.PortalOptClient;

@RestController
@RequestMapping(value = "/portal_c/opt")
public class OperationConsumerController {
	@Autowired
	private PortalOptClient portalOptClient;

	@RequestMapping(value = "/get_admins", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	//@AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object getAdmins(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			
			vars.put("admin", admin);

			Integer pageIndex = 1;
			Integer pageSize = 20;
			if (request.getParameter("pageIndex") != null && request.getParameter("pageSize") != null) {
				pageIndex = Integer.parseInt((String) request.getParameter("pageIndex"));
				pageSize = Integer.parseInt((String) request.getParameter("pageSize"));
			}
			vars.put("pageIndex", pageIndex);
			vars.put("pageSize", pageSize);
			int startIndex = (pageIndex - 1) * pageSize;
			vars.put("startIndex", startIndex);

			if (null != request.getParameter("adminSearchName")) {
				String adminSearchName = request.getParameter("adminSearchName");
				if (!StringUtils.isEmpty(adminSearchName)) {
					vars.put("adminSearchName", adminSearchName.toString().toUpperCase());
				} else {
					vars.put("adminSearchName", null);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return portalOptClient.getAdmins(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	@RequestMapping(value = "/save_admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object saveAdmin(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			
			vars.put("admin", admin);

			if (null != request.getParameter("group")) {
				String group = String.valueOf(request.getParameter("group"));
				vars.put("current_group_id", group.split(",")[0]);
				vars.put("current_group_name", group.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			if (null != request.getParameter("agency")) {
				String agency = String.valueOf(request.getParameter("agency"));
				vars.put("current_agency_id", agency.split(",")[0]);
				vars.put("current_agency_name", agency.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			if (null != request.getParameter("department")) {
				String department = String.valueOf(request.getParameter("department"));
				vars.put("current_department_id", department.split(",")[0]);
				vars.put("current_department_name", department.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			if (null != request.getParameter("duty")) {
				String duty = String.valueOf(request.getParameter("duty"));
				vars.put("current_duty_id", duty.split(",")[0]);
				vars.put("current_duty_name", duty.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			if (null != request.getParameter("name")) {
				String name = String.valueOf(request.getParameter("name"));
				if (StringUtils.isEmpty(name)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("name", name);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			if (null != request.getParameter("code")) {
				String code = String.valueOf(request.getParameter("code"));
				if (StringUtils.isEmpty(code)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("code", code);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("operators")) {
				String operators = String.valueOf(request.getParameter("operators"));
				vars.put("operator_id", operators.split(",")[0]);
				vars.put("operator", operators.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return portalOptClient.saveAdmin(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	

	// 权限

	// 用户

	@RequestMapping(value = "/get_agency_roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAgencyRoles(HttpServletResponse response, HttpServletRequest request  ,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			
			vars.put("admin", admin);
			
			if (null != request.getParameter("admin_code")) {
				String admin_code = request.getParameter("admin_code");
				if (!StringUtils.isEmpty(admin_code)) {
					vars.put("admin_code", admin_code);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			return portalOptClient.getAgencyRoles(vars);
		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/set_agency_roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object setAgencyRoles(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			
			vars.put("admin", admin);
			if (null != request.getParameter("admin_code")) {
				String admin_code = request.getParameter("admin_code");
				if (!StringUtils.isEmpty(admin_code)) {
					vars.put("admin_code", admin_code);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			if (null != request.getParameter("operators")) {
				String operators = String.valueOf(request.getParameter("operators"));
				vars.put("operator_id", operators.split(",")[0]);
				vars.put("operator", operators.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}


			if (null != request.getParameter("role_id")) {
				String role_id = request.getParameter("role_id");
				if (!StringUtils.isEmpty(role_id)) {
					vars.put("role_id", role_id);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			
			

			if (null != request.getParameter("status")) {
				String status = request.getParameter("status");
				if (!StringUtils.isEmpty(status)) {
					if (Boolean.valueOf(status)) {
						vars.put("status", "1");
					} else {
						vars.put("status", "0");
					}

				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return portalOptClient.setAgencyRoles(vars);
		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR);
		}
	}
	
	
	//dict信息
	@RequestMapping(value = "/get_dict_group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	//@AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object getDictGroup(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			
			vars.put("admin", admin);

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return portalOptClient.getDictGroup(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}
	
	


}
