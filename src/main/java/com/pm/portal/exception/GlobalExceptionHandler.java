package com.pm.portal.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;

import lombok.extern.slf4j.Slf4j;



@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
		log.error("出现了异常");
		System.out.println("出现了异常");
		log.error("url {},msg {}",request.getRequestURL(),e.getMessage(),e); 
		e.printStackTrace();
		if (e instanceof GlobalException) {
			GlobalException ex = (GlobalException) e;
			return Result.error(ex.getCm());
		} else if (e instanceof BindException) {
			BindException ex = (BindException) e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		} else {
			log.error("server error");
			System.out.println("server error");
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
	
	
	//捕获全局异常,处理所有不可知的异常
	@ExceptionHandler(value=MyException2.class)
    Object handleException(Exception e,HttpServletRequest request){
		log.error("url {}, msg {}",request.getRequestURL(), e.getMessage()); 
		Map<String, Object> map = new HashMap<String, Object>();
	        map.put("code", 100);
	        map.put("msg", e.getMessage());
	        map.put("url", request.getRequestURL());
	        return map;
    }
	
	

	/**
	 * 功能描述：处理自定义异常 见小d
	 * @return
	 */
	@ExceptionHandler(value=MyException.class)
    Object handleMyException(MyException e,HttpServletRequest request){
		//进行页面跳转
//		ModelAndView modelAndView = new ModelAndView();
//	    modelAndView.setViewName("error.html");
//	    modelAndView.addObject("msg", e.getMessage());
//	    return modelAndView;
		
		//返回json数据，由前端去判断加载什么页面
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", e.getCm().getCode());
        map.put("msg", e.getCm().getMsg());
        map.put("url", request.getRequestURL());
        return map;
        
    }
}
