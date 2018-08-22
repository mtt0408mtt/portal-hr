package com.pm.portal.service.sjhc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SjJgRkmxClient {
    private final RestTemplate restTpl;

    @Autowired
    public SjJgRkmxClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sjJgRkmxListGroup", commandKey = "sjJgRkmxListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgRkmx/list", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sjJgRkmxInsertGroup", commandKey = "sjJgRkmxInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgRkmx/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "sjJgRkmxUpdateGroup", commandKey = "sjJgRkmxUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgRkmx/edit", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "sjJgRkmxDeleteGroup", commandKey = "sjJgRkmxDeleteCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgRkmx/remove", vars, String.class);
    }

    private String listFallback(Map<String, Object> vars) {
        return "sjJgRkmxFail";
    }

    private String insertFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String updateFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String deleteFallback(Map<String, Object> vars) {
        return "fail";
    }
}
