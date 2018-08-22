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
public class ZkJgZkbzClient {
    private final RestTemplate restTpl;

    @Autowired
    public ZkJgZkbzClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listBzFallback", groupKey = "listBzGroup", commandKey = "listBzCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String listBz(Map<String, Object> vars) {
         String result = restTpl.postForObject("http://qc/qc/zkJgZkbz/listBz", vars, String.class);
         System.out.println("===="+result);
         return result;
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "zkbzInsertGroup", commandKey = "zkbzInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/zkJgZkbz/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "zkbzUpdateGroup", commandKey = "zkbzUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/zkJgZkbz/edit", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "zkbzDeleteGroup", commandKey = "zkbzDeleteCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Map<String, Object> vars) {
        return restTpl.postForObject("http://qc/qc/zkJgZkbz/remove", vars, String.class);
    }
    
    public String getZkJgZkbzListPage(Map<String, Object> vars) {
        String result = restTpl.postForObject("http://qc/qc/zkJgZkbz/getZkJgZkbzListPage", vars, String.class);
        System.out.println("===="+result);
        return result;
   }


    


    private String listBzFallback(Map<String, Object> vars) {
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
