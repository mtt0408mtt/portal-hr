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
public class LjgSjxmClient {
    private final RestTemplate restTpl;

    @Autowired
    public LjgSjxmClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "ljgSjxmListGroup", commandKey = "ljgSjxmListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
         String result = restTpl.postForObject("http://qc/qc/ljgSjxm/list", vars, String.class);
         System.out.println("===="+result);
         return result;
    }
    
    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "ljgSjxmGroup", commandKey = "ljgSjxmCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String listSjxm(Map<String, Object> vars) {
         String result = restTpl.postForObject("http://qc/qc/ljgSjxm/listSjxm", vars, String.class);
         System.out.println("===="+result);
         return result;
    }
    
    

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "ljgSjxmInsertGroup", commandKey = "ljgSjxmInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgSjxm/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "ljgSjxmUpdateGroup", commandKey = "ljgSjxmUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgSjxm/edit", vars, String.class);
    }
    
    public String editJy(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgSjxm/editJy", vars, String.class);
    }
    public String editJsgs(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgSjxm/editJsgs", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "ljgSjxmDeleteGroup", commandKey = "ljgSjxmDeleteCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgSjxm/remove", vars, String.class);
    }
    
    public String listCkz(Map<String, Object> vars) {
        String result = restTpl.postForObject("http://qc/qc/ljgSjxmCkz/list", vars, String.class);
        System.out.println("===="+result);
        return result;
   }
    
    public String insertCkz(Map<String, Object> vars) {
        String result = restTpl.postForObject("http://qc/qc/ljgSjxmCkz/add", vars, String.class);
        System.out.println("===="+result);
        return result;
   }
    
    public String updateCkz(Map<String, Object> vars) {
        String result = restTpl.postForObject("http://qc/qc/ljgSjxmCkz/edit", vars, String.class);
        System.out.println("===="+result);
        return result;
   }
    
    public String deleteCkz(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/ljgSjxmCkz/remove", vars, String.class);
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
