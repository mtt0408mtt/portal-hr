package com.pm.portal.service.lis;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class LjgJymdClient {
    private final RestTemplate restTpl;

    @Autowired
    public LjgJymdClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "ljgJymdListGroup", commandKey = "ljgJymdListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
         String result = restTpl.postForObject("http://qc/qc/ljgJymd/list", vars, String.class);
         System.out.println("===="+result);
         return result;
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "ljgJymdInsertGroup", commandKey = "ljgJymdInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgJymd/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "ljgJymdUpdateGroup", commandKey = "ljgJymdUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgJymd/edit", vars, String.class);
    }
    
    public String editJy(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgJymd/editJy", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "ljgJymdDeleteGroup", commandKey = "ljgJymdDeleteCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgJymd/remove", vars, String.class);
    }
    
    public String listmx(Map<String, Object> vars) {
        String result = restTpl.postForObject("http://qc/qc/ljgJymdmx/list", vars, String.class);
        System.out.println("===="+result);
        return result;
   }
    
    public String insertmx(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgJymdmx/addmx", vars, String.class);
    }
    
    public String deletemx(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgJymdmx/remove", vars, String.class);
    }
    
    public String savemx(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgJymdmx/savemx", vars, String.class);
    }
    
    

    private String listFallback(Map<String, Object> vars) {
        return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
    }

    private String insertFallback(Map<String, Object> vars) {
        return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
    }

    private String updateFallback(Map<String, Object> vars) {
        return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
    }

    private String deleteFallback(Map<String, Object> vars) {
        return JSONObject.toJSONString(Result.error(CodeMsg.LIS_SERVER_ERROR));
    }
}
