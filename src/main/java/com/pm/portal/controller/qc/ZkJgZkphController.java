package com.pm.portal.controller.qc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.qc.ZkJgZkphClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/zkJgZkph")
public class ZkJgZkphController {
    private final ZkJgZkphClient zkJgZkphClient;

    @Autowired
    public ZkJgZkphController(ZkJgZkphClient zkJgZkphClient) {
        this.zkJgZkphClient = zkJgZkphClient;
    }


    
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCombination(HttpServletResponse response, HttpServletRequest request, MhAdmin admin) {
		Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin", admin);
			vars.put("jtbh", "1");
	        vars.put("jgid", "32110001");
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
		/*	if (null != request.getParameter("agency") && null != request.getParameter("group")){
				vars.put("agency", request.getParameter("agency"));
				vars.put("group", request.getParameter("group"));
			}else{
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}*/
			

		/*	if (null != request.getParameter("condition") && null != request.getParameter("combSearchName") ){
				String combSearchName=request.getParameter("combSearchName");
				if(!StringUtils.isEmpty(combSearchName)){
					vars.put("condition", request.getParameter("condition"));
					vars.put("combSearchName", combSearchName.toString().toUpperCase());
				}

			}else{
				return Result.error(CodeMsg.LIS_PARAMS_ERROR);
			}*/
			return zkJgZkphClient.list(vars);
	

		} catch (	

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
    
    @RequestMapping(value = "/listByJhid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object listByJhid(HttpServletResponse response, HttpServletRequest request) {
   		Map<String, Object> vars = new HashMap<String, Object>();
   		try {
   			String jhid=request.getParameter("jhid");
   			vars.put("jhid", jhid);
   			vars.put("jtbh", "1");
   	        vars.put("jgid", "32110001");
   			return zkJgZkphClient.listByJhid(vars);
   	

   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}
    
    @RequestMapping(value = "/listByYq", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object listNotPh(HttpServletResponse response, HttpServletRequest request) {
   		Map<String, Object> vars = new HashMap<String, Object>();
   		try {
   			String jhid=request.getParameter("jhid");
   			vars.put("jhid", jhid);
   			String yqbh=request.getParameter("yqbh");
   			vars.put("yqbh", yqbh);
   			vars.put("jtbh", "1");
   	        vars.put("jgid", "32110001");
   			return zkJgZkphClient.listByYq(vars);
   	

   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> vars) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //Map<String, Object> vars = new HashMap<String, Object>();
            String name =request.getParameter("zkpmc");
            vars.put("jtbh", "1");
            vars.put("jgid", "32110001");
            
            result.put("message", zkJgZkphClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response,@RequestBody Map<String, Object> vars) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result.put("message", zkJgZkphClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response, Integer id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("id", id);
            result.put("message", zkJgZkphClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "zkJgZkphRemoveFail");
        }
        return result;
    }
}
