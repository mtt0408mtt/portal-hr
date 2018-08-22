package com.pm.portal.service.sjhc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SjJgWpxxCsjgClient {
    private final RestTemplate restTpl;

    @Autowired
    public SjJgWpxxCsjgClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sjJgWpxxCsjgListGroup", commandKey = "sjJgWpxxCsjgListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgWpxxCsjg/list", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sjJgWpxxCsjgInsertGroup", commandKey = "sjJgWpxxCsjgInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgWpxxCsjg/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "sjJgWpxxCsjgUpdateGroup", commandKey = "sjJgWpxxCsjgUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgWpxxCsjg/edit", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "disableFallback", groupKey = "sjJgWpxxCsjgDisableGroup", commandKey = "sjJgWpxxCsjgDisableCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String disable(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgWpxxCsjg/cancel", vars, String.class);
    }

    private String listFallback(Map<String, Object> vars) {
        return "sjJgWpxxCsjgFail";
    }

    private String insertFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String updateFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String disableFallback(Map<String, Object> vars) {
        return "fail";
    }
}
