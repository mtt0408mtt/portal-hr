package com.pm.portal.controller.lis;

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
import com.pm.portal.service.lis.LjgSjxmClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/ljgSjxm")
public class LjgSjxmController {
    private final LjgSjxmClient ljgSjxmClient;

    @Autowired
    public LjgSjxmController(LjgSjxmClient ljgSjxmClient) {
        this.ljgSjxmClient = ljgSjxmClient;
    }


    
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCombination(HttpServletResponse response, HttpServletRequest request,
			@RequestBody(required=false) Map<String, Object> vars,MhAdmin admin) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		//Map<String, Object> vars = new HashMap<String, Object>();
		try {

			if(vars.get("pym") != null||!vars.get("pym").toString().equals("")){
				if(vars.get("isZwm") != null){
					vars.put("xmzwm", vars.get("pym").toString());
					vars.put("pym", null);
				}else{
					vars.put("pym", vars.get("pym").toString());
					vars.put("xmzwm", null);
				}
			}else{
				vars.remove("pym");
				vars.remove("xmzwm");
			}
			
			
			Integer pageIndex = 1;
			Integer pageSize = 20;
			if (vars.get("pageIndex") != null && vars.get("pageSize") != null) {
				pageIndex = Integer.parseInt( vars.get("pageIndex").toString());
				pageSize = Integer.parseInt(vars.get("pageSize").toString());
			}
			int startIndex=(pageIndex - 1) * pageSize;
			vars.put("startIndex", startIndex);
			vars.put("admin", admin);
			
			
			return ljgSjxmClient.list(vars);
	

		} catch (	

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
    @RequestMapping(value = "/listSjxm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object listSjxm(HttpServletResponse response, HttpServletRequest request,
   			@RequestBody(required=false) Map<String, Object> vars,MhAdmin admin) {
       	response.setHeader("Access-Control-Allow-Origin", "*");
   		//Map<String, Object> vars = new HashMap<String, Object>();
   		try {

   			if(vars.get("pym") == null||vars.get("pym").toString().equals("")){
   				vars.remove("pym");
   			}
   			if(vars.get("xmzwm") == null||vars.get("xmzwm").toString().equals("")){
   				vars.remove("xmzwm");
   			}
   			if(vars.get("xmid") == null||vars.get("xmid").toString().equals("")){
   				vars.remove("xmid");
   			}		
   			Integer pageIndex = 1;
   			Integer pageSize = 20;
   			if (vars.get("pageIndex") != null && vars.get("pageSize") != null) {
   				pageIndex = Integer.parseInt( vars.get("pageIndex").toString());
   				pageSize = Integer.parseInt(vars.get("pageSize").toString());
   			}
   			int startIndex=(pageIndex - 1) * pageSize;
   			vars.put("startIndex", startIndex);
   			vars.put("admin", admin);
   			
   			
   			return ljgSjxmClient.listSjxm(vars);
   	

   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insert(HttpServletResponse response,HttpServletRequest request,
    		@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	String syyq =request.getParameter("yqbhs");
        	String qdsj1 =request.getParameter("qdsj");
        	String qdsj2 =request.getParameter("qdsj2");
        	String qdsj3 =request.getParameter("qdsj3");
        	vars.put("qdsj",qdsj1+"_"+qdsj2+"_"+qdsj3);
            vars.put("syyq",syyq);
            vars.put("szsj",new Date());
            vars.put("admin", admin);
            result.put("message", ljgSjxmClient.insert(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update(HttpServletResponse response,HttpServletRequest request,
    		@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	String syyq =request.getParameter("yqbhs");
        	String qdsj1 =request.getParameter("qdsj1");
        	String qdsj2 =request.getParameter("qdsj2");
        	String qdsj3 =request.getParameter("qdsj3");
        	vars.put("qdsj",qdsj1+"_"+qdsj2+"_"+qdsj3);
            vars.put("syyq",syyq);
            vars.put("szsj",new Date());
            vars.put("admin", admin);
            result.put("message", ljgSjxmClient.update(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/editJsgs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object editJsgs(HttpServletResponse response,HttpServletRequest request,
    		@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	
            vars.put("admin", admin);
            result.put("message", ljgSjxmClient.editJsgs(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/editJy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object editJy(HttpServletResponse response,@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	vars.put("admin", admin);
            result.put("message", ljgSjxmClient.editJy(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(HttpServletResponse response,@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	vars.put("admin", admin);
            result.put("message", ljgSjxmClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "ljgSjxmRemoveFail");
        }
        return result;
    }
    
    @RequestMapping(value = "/listCkz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   	public Object listCkz(HttpServletResponse response, HttpServletRequest request,
   			@RequestBody(required=false) Map<String, Object> vars,MhAdmin admin) {
       	response.setHeader("Access-Control-Allow-Origin", "*");

   		try {	
   			vars.put("admin", admin);
   			return ljgSjxmClient.listCkz(vars);
   		} catch (	

   		Exception e) {
   			e.printStackTrace();
   			return Result.error(CodeMsg.LIS_APPLE_ERROR);
   		}
   	}
    
    
    @RequestMapping(value = "/addCkz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertCkz(HttpServletResponse response,HttpServletRequest request,
    		@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("admin", admin);
            result.put("message", ljgSjxmClient.insertCkz(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    
    @RequestMapping(value = "/editCkz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateCkz(HttpServletResponse response,HttpServletRequest request,
    		@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            vars.put("admin", admin);
            result.put("message", ljgSjxmClient.updateCkz(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    @RequestMapping(value = "/removeCkz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteCkz(HttpServletResponse response,@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	vars.put("admin", admin);
            result.put("message", ljgSjxmClient.deleteCkz(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "ljgSjxmRemoveFail");
        }
        return result;
    }
    
  
}
