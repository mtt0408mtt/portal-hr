package com.pm.portal.service.qc;

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
public class ZkJgResultClient {
    private final RestTemplate restTpl;

    @Autowired
    public ZkJgResultClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "zkJgResultListGroup", commandKey = "zkJgResultListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
         String result = restTpl.postForObject("http://qc/qc/zkJgResult/list", vars, String.class);
         System.out.println("===="+result);
         return result;
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "zkJgResultInsertGroup", commandKey = "zkJgResultInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/zkJgResult/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "zkJgResultUpdateGroup", commandKey = "zkJgResultUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/zkJgResult/edit", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "zkJgResultDeleteGroup", commandKey = "zkJgResultDeleteCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/qc/qc/zkjgZkph/remove", vars, String.class);
    }

    public String PdGzResult(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/zkJgResult/PdGzResult", vars, String.class);
    }
    
    public String getlistZkt(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/zkJgResult/getlistZkt", vars, String.class);
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
