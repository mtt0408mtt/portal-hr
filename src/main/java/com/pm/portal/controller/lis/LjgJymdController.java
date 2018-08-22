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
import com.pm.portal.service.lis.LjgJymdClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/ljgJymd")
public class LjgJymdController {
    private final LjgJymdClient ljgJymdClient;

    @Autowired
    public LjgJymdController(LjgJymdClient ljgJymdClient) {
        this.ljgJymdClient = ljgJymdClient;
    }


    
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getCombination(HttpServletResponse response, HttpServletRequest request,
			@RequestBody(required=false) Map<String, Object> vars,MhAdmin admin) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		//Map<String, Object> vars = new HashMap<String, Object>();
		try {

			if(vars.get("pym") != null){
				if(vars.get("isZwm") != null){
					vars.put("zhmc", vars.get("pym").toString());
					vars.put("pym", null);
				}else{
					vars.put("pym", vars.get("pym").toString());
					vars.put("zhmc", null);
				}
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
			
			
			return ljgJymdClient.list(vars);
	

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
            result.put("message", ljgJymdClient.insert(vars));
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
            result.put("message", ljgJymdClient.update(vars));
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
            result.put("message", ljgJymdClient.editJy(vars));
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
            result.put("message", ljgJymdClient.delete(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "ljgJymdRemoveFail");
        }
        return result;
    }
    
    @RequestMapping(value = "/listmx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object listmx(HttpServletResponse response, HttpServletRequest request,
			@RequestBody(required=false) Map<String, Object> vars,MhAdmin admin) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			
			vars.put("admin", admin);
			return ljgJymdClient.listmx(vars);	

		} catch (	

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.LIS_APPLE_ERROR);
		}
	}
    
    @RequestMapping(value = "/removemx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deletemx(HttpServletResponse response,@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
        	vars.put("admin", admin);
            result.put("message", ljgJymdClient.deletemx(vars));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "ljgJymdRemoveFail");
        }
        return result;
    }
    
    @RequestMapping(value = "/savemx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertmx(HttpServletResponse response,HttpServletRequest request,
    		@RequestBody Map<String, Object> vars,MhAdmin admin) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<String, Object> result = new HashMap<String, Object>();
        try {        	
            vars.put("admin", admin);
            String message=ljgJymdClient.savemx(vars);
            result.put("message", message);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "fail");
        }
        return result;
    }
    
  
}
