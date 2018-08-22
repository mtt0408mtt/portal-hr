package com.pm.portal.controller.qc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.qc.ZkJgZkjhClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/zkJgZkjh")
public class ZkJgZkJhController {
    private final ZkJgZkjhClient zkJgZkjhClient;

    @Autowired
    public ZkJgZkJhController(ZkJgZkjhClient zkJgZkjhClient) {
        this.zkJgZkjhClient = zkJgZkjhClient;
    }


    
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCombination(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		//Map<String, Object> vars = new HashMap<String, Object>();
		try {
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
			return zkJgZkjhClient.list(vars);
	

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
            vars.put("szr","1");
            vars.put("szsj",new Date());
            
            result.put("message", zkJgZkjhClient.insert(vars));
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
            result.put("message", zkJgZkjhClient.update(vars));
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
            result.put("message", zkJgZkjhClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "zkJgZkjhRemoveFail");
        }
        return result;
    }
    
    @RequestMapping(value = "/saveJhph", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveJhph(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> vars) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110001");
            
            result.put("message", zkJgZkjhClient.saveJhph(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    @RequestMapping(value = "/delJhph", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delJhph(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> vars) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110001");
            
            result.put("message", zkJgZkjhClient.delJhph(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/listGz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object listGz(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars) {
   		try {
   			vars.put("jtbh", "1");
   	        vars.put("jgid", "32110001");
   			return zkJgZkjhClient.listGz(vars);
   	

   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}
    
    @RequestMapping(value = "/listGzNoJh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object listGzNoJh(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars) {
   		try {
   			String jhid=request.getParameter("jhid");
   			vars.put("jtbh", "1");
   	        vars.put("jgid", "32110001");
   			return zkJgZkjhClient.listGzNoJh(vars);
   	

   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}
    
    @RequestMapping(value = "/saveJhgz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object saveJhgz(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> vars) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110001");
            
            result.put("message", zkJgZkjhClient.saveJhgz(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/delJhgz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delJhgz(HttpServletResponse response,HttpServletRequest request,@RequestBody Map<String, Object> vars) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("jtbh", "1");
            vars.put("jgid", "32110001");
            
            result.put("message", zkJgZkjhClient.delJhgz(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/listJhByYq", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object listJhByYq(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars) {
   		try {
   			String jhid=request.getParameter("jhid");
   			vars.put("jtbh", "1");
   	        vars.put("jgid", "32110001");
   			return zkJgZkjhClient.listJhByYq(vars);
   	

   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}
    
    @RequestMapping(value = "/listBz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
 	public Object listBz(HttpServletResponse response, HttpServletRequest request,@RequestBody(required=false) Map<String, Object> vars) {
     	response.setHeader("Access-Control-Allow-Origin", "*");
 		//Map<String, Object> vars = new HashMap<String, Object>();
 		try {
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

 			return zkJgZkjhClient.listBz(vars);
 	

 		} catch (	

 		Exception e) {
 			e.printStackTrace();
 			return Result.error(CodeMsg.LIS_APPLE_ERROR);
 		}
 	}
    
    
    
}
