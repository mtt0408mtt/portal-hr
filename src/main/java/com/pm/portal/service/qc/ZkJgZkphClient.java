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
public class ZkJgZkphClient {
    private final RestTemplate restTpl;

    @Autowired
    public ZkJgZkphClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sjJgXtcsListGroup", commandKey = "sjJgXtcsListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
         String result = restTpl.postForObject("http://pm-zuul-gateway/qc/qc/zkJgZkph/list", vars, String.class);
         System.out.println("===="+result);
         return result;
    }
    public String listByJhid(Map<String, Object> vars) {
        String result = restTpl.postForObject("http://pm-zuul-gateway/qc/qc/zkJgZkph/listByJhid", vars, String.class);
        System.out.println("===="+result);
        return result;
   }
    public String listByYq(Map<String, Object> vars) {
        String result = restTpl.postForObject("http://pm-zuul-gateway/qc/qc/zkJgZkph/listByYq", vars, String.class);
        System.out.println("===="+result);
        return result;
   }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sjJgXtcsInsertGroup", commandKey = "sjJgXtcsInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/qc/qc/zkJgZkph/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "sjJgXtcsUpdateGroup", commandKey = "sjJgXtcsUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/qc/qc/zkJgZkph/edit", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "sjJgXtcsDeleteGroup", commandKey = "sjJgXtcsDeleteCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/qc/qc/zkjgZkph/remove", vars, String.class);
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
