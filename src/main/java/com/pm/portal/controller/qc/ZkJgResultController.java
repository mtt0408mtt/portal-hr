package com.pm.portal.controller.qc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.qc.ZkJgResultClient;
import com.pm.portal.vo.LoginVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/zkJgResult")
public class ZkJgResultController {
    private final ZkJgResultClient zkJgResultClient;

    @Autowired
    public ZkJgResultController(ZkJgResultClient zkJgResultClient) {
        this.zkJgResultClient = zkJgResultClient;
    }


    
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCombination(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars,MhAdmin admin) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		//Map<String, Object> vars = new HashMap<String, Object>();
		try {
			vars.put("admin",admin);
			vars.put("jtbh", "1");
	        vars.put("jgid", "32110001");
			Integer pageIndex = 1;
			Integer pageSize = 20;
			if (vars.get("pageIndex") != null && vars.get("pageSize") != null) {
				pageIndex = Integer.parseInt( vars.get("pageIndex").toString());
				pageSize = Integer.parseInt(vars.get("pageSize").toString());
			}
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
			return zkJgResultClient.list(vars);
	

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
            vars.put("jtbh", "1");
            vars.put("jgid", "32110001");
            
            result.put("message", zkJgResultClient.insert(vars));
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
        	vars.put("jtbh", "1");
            vars.put("jgid", "32110001");
            result.put("message", zkJgResultClient.update(vars));
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
            result.put("message", zkJgResultClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "zkJgResultRemoveFail");
        }
        return result;
    }
    
    @RequestMapping(value = "/PdGzResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object PdGzResult(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars) {
       	response.setHeader("Access-Control-Allow-Origin", "*");
   		//Map<String, Object> vars = new HashMap<String, Object>();
   		try {
   			vars.put("jtbh", "1");
   	        vars.put("jgid", "32110001");

   	        return zkJgResultClient.PdGzResult(vars);

   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}
    
    @RequestMapping(value = "/getlistZkt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  	public Object getlistZkt(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars) {
      	response.setHeader("Access-Control-Allow-Origin", "*");
  		//Map<String, Object> vars = new HashMap<String, Object>();
  		try {
  			vars.put("jtbh", "1");
  	        vars.put("jgid", "32110001");
  			return zkJgResultClient.getlistZkt(vars);
  	

  		} catch (	

  		Exception e) {
  			e.printStackTrace();
  			return Result.error(CodeMsg.LIS_APPLE_ERROR);
  		}
  	}
    
    @RequestMapping("/printZkt")
	@ResponseBody
  	public Object printZkt(HttpServletResponse response, HttpServletRequest request ) {
      	response.setHeader("Access-Control-Allow-Origin", "*");
      Map<String, Object> vars = new HashMap<String, Object>();
      String yqbh=request.getParameter("yqbh");
      String jhid=request.getParameter("jhid");
      String zkbh=request.getParameter("zkbh");
      String maxRq=request.getParameter("maxRq");
      String minRq=request.getParameter("minRq");
      String xmid=request.getParameter("xmid");
      vars.put("yqbh", yqbh);
      vars.put("jhid", jhid);
      vars.put("zkbh", zkbh);
	  vars.put("maxRq", maxRq);
	  vars.put("minRq",minRq);
	  vars.put("xmid",xmid);

      
  		try {
  			vars.put("jtbh", "1");
  	        vars.put("jgid", "32110001");
  			return zkJgResultClient.getlistZkt(vars);
  	

  		} catch (	

  		Exception e) {
  			e.printStackTrace();
  			return Result.error(CodeMsg.LIS_APPLE_ERROR);
  		}
  	}
    

    
   
    
    
    
}
