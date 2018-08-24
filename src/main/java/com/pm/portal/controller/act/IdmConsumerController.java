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
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			PMDataGridResult<List<MhAdmin>> users = idmClient.getUsers(vars);

			return Result.success(users);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
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
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);

			}

			if (null != request.getParameter("email")) {
				String email = request.getParameter("email");
				if (!StringUtils.isEmpty(email)) {
					vars.put("email", email);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("firstName")) {
				String firstName = request.getParameter("firstName");
				if (!StringUtils.isEmpty(firstName)) {
					vars.put("firstName", firstName);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("lastName")) {
				String lastName = request.getParameter("lastName");
				if (!StringUtils.isEmpty(lastName)) {
					vars.put("lastName", lastName);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);

			}
			if (null != request.getParameter("password")) {
				String password = request.getParameter("password");
				if (!StringUtils.isEmpty(password)) {
					vars.put("password", password);
				} else {
					return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);
				}

			} else {
				return Result.error(CodeMsg.PORTAL_OPT_PARAMS_ERROR);

			}

			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}
			idmClient.addUser(vars);

			return Result.ok();

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.MESSAGE01);
		}
	}

}
