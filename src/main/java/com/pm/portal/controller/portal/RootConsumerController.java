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
import com.pm.portal.service.portal.PortalRootClient;

@RestController
@RequestMapping(value = "/portal_c/root")
public class RootConsumerController {
	@Autowired
	private PortalRootClient portalRootClient;


	// agency====================================================================================

	@RequestMapping(value = "/get_agencys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getAgencys(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
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

			if (null != request.getParameter("agencySearchName")) {
				String agencySearchName = request.getParameter("agencySearchName");
				if (!StringUtils.isEmpty(agencySearchName)) {
					vars.put("agencySearchName", agencySearchName.toString().toUpperCase());
				} else {
					vars.put("agencySearchName", null);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return portalRootClient.getAgencys(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	@RequestMapping(value = "/save_agency", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object saveAgency(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			
			vars.put("admin", admin);

			if (null != request.getParameter("group")) {
				String group = String.valueOf(request.getParameter("group"));
				vars.put("group_id", group.split(",")[0]);
				vars.put("group_name", group.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("type")) {
				String type = String.valueOf(request.getParameter("type"));
				if (StringUtils.isEmpty(type)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("type", type);
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

			if (null != request.getParameter("desc")) {
				String desc = String.valueOf(request.getParameter("desc"));
				if (StringUtils.isEmpty(desc)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("desc", desc);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("comment")) {
				String comment = String.valueOf(request.getParameter("comment"));
				if (StringUtils.isEmpty(comment)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("comment", comment);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("address")) {
				String address = String.valueOf(request.getParameter("address"));
				if (StringUtils.isEmpty(address)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("address", address);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("post_code")) {
				String post_code = String.valueOf(request.getParameter("post_code"));
				if (StringUtils.isEmpty(post_code)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("post_code", post_code);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("contact_name")) {
				String contact_name = String.valueOf(request.getParameter("contact_name"));
				if (StringUtils.isEmpty(contact_name)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("contact_name", contact_name);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("contact_phone")) {
				String contact_phone = String.valueOf(request.getParameter("contact_phone"));
				if (StringUtils.isEmpty(contact_phone)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("contact_phone", contact_phone);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("fax")) {
				String fax = String.valueOf(request.getParameter("fax"));
				if (StringUtils.isEmpty(fax)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("fax", fax);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("email")) {
				String email = String.valueOf(request.getParameter("email"));
				if (StringUtils.isEmpty(email)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("email", email);
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

			return portalRootClient.saveAgency(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	// role====================================================================================

	@RequestMapping(value = "/get_roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getRoles(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
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

			if (null != request.getParameter("roleSearchName")) {
				String roleSearchName = request.getParameter("roleSearchName");
				if (!StringUtils.isEmpty(roleSearchName)) {
					vars.put("roleSearchName", roleSearchName.toString().toUpperCase());
				} else {
					vars.put("roleSearchName", null);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return portalRootClient.getRoles(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	@RequestMapping(value = "/save_role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object saveRole(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);
			
			if (null != request.getParameter("name")) {
				String name = String.valueOf(request.getParameter("name"));
				if (StringUtils.isEmpty(name)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("name", name);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			
			
			if (null != request.getParameter("group")) {
				String agency = String.valueOf(request.getParameter("group"));
				vars.put("group_id", agency.split(",")[0]);
				vars.put("group_name", agency.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("agency")) {
				String agency = String.valueOf(request.getParameter("agency"));
				vars.put("agency_id", agency.split(",")[0]);
				vars.put("agency_name", agency.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("comment")) {
				String comment = String.valueOf(request.getParameter("comment"));
				if (StringUtils.isEmpty(comment)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("comment", comment);
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

			return portalRootClient.saveRole(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	// res====================================================================================

	@RequestMapping(value = "/get_reses", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getReses(HttpServletResponse response, HttpServletRequest request ,MhAdmin admin) {
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

			if (null != request.getParameter("resSearchName")) {
				String resSearchName = request.getParameter("resSearchName");
				if (!StringUtils.isEmpty(resSearchName)) {
					vars.put("resSearchName", resSearchName.toString().toUpperCase());
				} else {
					vars.put("resSearchName", null);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return portalRootClient.getReses(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	@RequestMapping(value = "/save_res", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object saveRes(HttpServletResponse response, HttpServletRequest request  ,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);
			
			if (null != request.getParameter("name")) {
				String name = String.valueOf(request.getParameter("name"));
				if (StringUtils.isEmpty(name)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("name", name);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("subsys")) {
				String subsys = String.valueOf(request.getParameter("subsys"));
				vars.put("subsys_id", subsys.split(",")[0]);
				vars.put("subsys_name", subsys.split(",")[1]);
				vars.put("subsys_path", subsys.split(",")[2]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("depart")) {
				String depart = String.valueOf(request.getParameter("depart"));
				vars.put("depart_id", depart.split(",")[0]);
				vars.put("depart_name", depart.split(",")[1]);
				vars.put("depart_code", depart.split(",")[2]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("group")) {
				String agency = String.valueOf(request.getParameter("group"));
				vars.put("group_id", agency.split(",")[0]);
				vars.put("group_name", agency.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("agency")) {
				String agency = String.valueOf(request.getParameter("agency"));
				vars.put("agency_id", agency.split(",")[0]);
				vars.put("agency_name", agency.split(",")[1]);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("comment")) {
				String comment = String.valueOf(request.getParameter("comment"));
				if (StringUtils.isEmpty(comment)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("comment", comment);
			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			if (null != request.getParameter("res_url")) {
				String res_url = String.valueOf(request.getParameter("res_url"));
				if (StringUtils.isEmpty(res_url)) {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}
				vars.put("res_url", res_url);
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

			return portalRootClient.saveRes(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR);
		}
	}

	// 权限

	// 用户


	//角色
	@RequestMapping(value = "/get_role_reses", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getRoleReses(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("admin", admin);
			
			if (null != request.getParameter("role_name")) {
				String role_name = request.getParameter("role_name");
				if (!StringUtils.isEmpty(role_name)) {
					vars.put("role_name", role_name);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			return portalRootClient.getRoleReses(vars);
		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.PORTAL_OPT_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/set_role_reses", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object setRoleReses(HttpServletResponse response, HttpServletRequest request,MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();
			vars.put("admin", admin);
			if (null != request.getParameter("role_name")) {
				String role_name = request.getParameter("role_name");
				if (!StringUtils.isEmpty(role_name)) {
					vars.put("role_name", role_name);
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


			if (null != request.getParameter("res_id")) {
				String res_id = request.getParameter("res_id");
				if (!StringUtils.isEmpty(res_id)) {
					vars.put("res_id", res_id);
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

			return portalRootClient.setRoleReses(vars);
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

			return portalRootClient.getDictGroup(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

}
