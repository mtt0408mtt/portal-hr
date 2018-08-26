package com.pm.portal.controller.act;

import java.util.HashMap;
import java.util.List;
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
import com.pm.portal.domain.portal.MhGroup;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.PMDataGridResult;
import com.pm.portal.result.Result;
import com.pm.portal.service.act.IdmClient;
import com.pm.portal.service.portal.PortalOptClient;

@RestController
@RequestMapping(value = "/idm")
public class IdmConsumerController {

	@Autowired
	private IdmClient idmClient;

	@RequestMapping(value = "/get_users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object getUsers(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
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

			if (null != request.getParameter("filter")) {
				String filter = request.getParameter("filter");
				if (!StringUtils.isEmpty(filter)) {
					vars.put("filter", filter.toString().toUpperCase());
				} else {
					vars.put("filter", null);
				}

			}

			if (null != request.getParameter("sort")) {
				String sort = request.getParameter("sort");
				if (!StringUtils.isEmpty(sort)) {
					vars.put("sort", sort);
				} else {
					vars.put("sort", null);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			PMDataGridResult<List<MhAdmin>> users = idmClient.getUsers(vars);

			return Result.success(users);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	// add_user

	@RequestMapping(value = "/add_user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object addUser(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);

			if (null != request.getParameter("id")) {
				String id = request.getParameter("id");
				if (!StringUtils.isEmpty(id)) {
					vars.put("id", id);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}

			if (null != request.getParameter("email")) {
				String email = request.getParameter("email");
				if (!StringUtils.isEmpty(email)) {
					vars.put("email", email);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("firstName")) {
				String firstName = request.getParameter("firstName");
				if (!StringUtils.isEmpty(firstName)) {
					vars.put("firstName", firstName);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("lastName")) {
				String lastName = request.getParameter("lastName");
				if (!StringUtils.isEmpty(lastName)) {
					vars.put("lastName", lastName);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("password")) {
				String password = request.getParameter("password");
				if (!StringUtils.isEmpty(password)) {
					vars.put("password", password);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			return idmClient.addUser(vars);


		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	// update_user

	@RequestMapping(value = "/update_user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object updateUser(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);

			if (null != request.getParameter("id_")) {
				String id_ = request.getParameter("id_");
				if (!StringUtils.isEmpty(id_)) {
					vars.put("id_", id_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}

			if (null != request.getParameter("email_")) {
				String email_ = request.getParameter("email_");
				if (!StringUtils.isEmpty(email_)) {
					vars.put("email_", email_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("first_")) {
				String first_ = request.getParameter("first_");
				if (!StringUtils.isEmpty(first_)) {
					vars.put("first_", first_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("last_")) {
				String last_ = request.getParameter("last_");
				if (!StringUtils.isEmpty(last_)) {
					vars.put("last_", last_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			idmClient.updateUser(vars);

			return Result.ok();

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	// update_users_password

	@RequestMapping(value = "/update_users_password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object updateUserPassword(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);

			if (null != request.getParameterValues("users")) {
				String[] users = request.getParameterValues("users");
				vars.put("users", users);
			} else {
				Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}

			if (null != request.getParameter("pwd_")) {
				String pwd_ = request.getParameter("pwd_");
				if (!StringUtils.isEmpty(pwd_)) {
					vars.put("pwd_", pwd_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			idmClient.updateUserPassword(vars);

			return Result.ok();

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	// delete_user

	@RequestMapping(value = "/delete_user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object deleteUser(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);

			if (null != request.getParameter("id_")) {
				String id_ = request.getParameter("id_");
				if (!StringUtils.isEmpty(id_)) {
					vars.put("id_", id_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);

			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			idmClient.deleteUser(vars);

			return Result.ok();

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	// get_groups
	@RequestMapping(value = "/get_groups", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object getGroups(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			List<MhGroup> groups = idmClient.getGroups(vars);

			return Result.success(groups);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	// get_group
	@RequestMapping(value = "/get_group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object getGroup(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);
			// 暂时不分页
			// Integer pageIndex = 1;
			// Integer pageSize = 20;
			// if (request.getParameter("pageIndex") != null &&
			// request.getParameter("pageSize") != null) {
			// pageIndex = Integer.parseInt((String)
			// request.getParameter("pageIndex"));
			// pageSize = Integer.parseInt((String)
			// request.getParameter("pageSize"));
			// }
			// vars.put("pageIndex", pageIndex);
			// vars.put("pageSize", pageSize);
			// int startIndex = (pageIndex - 1) * pageSize;
			// vars.put("startIndex", startIndex);

			if (null != request.getParameter("groupId")) {
				String groupId = request.getParameter("groupId");
				if (!StringUtils.isEmpty(groupId)) {
					vars.put("groupId", groupId);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			}else{
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			MhGroup group = idmClient.getGroup(vars);

			return Result.success(group);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/create_group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object createGroup(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);
			
			if (null != request.getParameter("id_")) {
				String id_ = request.getParameter("id_");
				if (!StringUtils.isEmpty(id_)) {
					vars.put("id_", id_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}
			}else{
				
				 return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}
			
			
			if (null != request.getParameter("name_")) {
				String name_ = request.getParameter("name_");
				if (!StringUtils.isEmpty(name_)) {
					vars.put("name_", name_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			}else{
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}
			
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			return idmClient.createGroup(vars);
			

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}

	}
	
	
	@RequestMapping(value = "/update_group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object updateGroup(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);
			
			if (null != request.getParameter("id_")) {
				String id_ = request.getParameter("id_");
				if (!StringUtils.isEmpty(id_)) {
					vars.put("id_", id_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}
			}else{
				
				 return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}
			
			
			if (null != request.getParameter("name_")) {
				String name_ = request.getParameter("name_");
				if (!StringUtils.isEmpty(name_)) {
					vars.put("name_", name_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}

			}else{
				return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}
			
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			 idmClient.updateGroup(vars);
			 return Result.ok();

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}

	}
	
	//delete_group
	
	@RequestMapping(value = "/delete_group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object deleteGroup(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);
			
			if (null != request.getParameter("id_")) {
				String id_ = request.getParameter("id_");
				if (!StringUtils.isEmpty(id_)) {
					vars.put("id_", id_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}
			}else{
				
				 return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}
			
			
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			 idmClient.deleteGroup(vars);
			 return Result.ok();

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}

	}
	
	//delete_groupmember
	
	@RequestMapping(value = "/delete_groupmember", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	// @AccessLimit(seconds=5, maxCount=5, needLogin=true)
	public Object deleteGroupmember(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			vars.put("admin", admin);
			
			if (null != request.getParameter("id_")) {
				String id_ = request.getParameter("id_");
				if (!StringUtils.isEmpty(id_)) {
					vars.put("id_", id_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}
			}else{
				
				 return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}
			
			if (null != request.getParameter("user_id_")) {
				String user_id_ = request.getParameter("user_id_");
				if (!StringUtils.isEmpty(user_id_)) {
					vars.put("user_id_", user_id_);
				} else {
					return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
				}
			}else{
				
				 return Result.error(CodeMsg.IDM_OPT_PARAMS_ERROR);
			}
			
			
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			 idmClient.deleteGroupmember(vars);
			 return Result.ok();

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.SERVER_ERROR);
		}

	}

}
