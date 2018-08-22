package com.pm.portal.service.sjhc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SjJgCsglClient {
    private final RestTemplate restTpl;

    @Autowired
    public SjJgCsglClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "fuzzyListFallback", groupKey = "sjJgCsglFuzzyListGroup", commandKey = "sjJgCsglFuzzyListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String fuzzyList(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgCsgl/fuzzy", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "fuzzyList2Fallback", groupKey = "sjJgCsglFuzzyList2Group", commandKey = "sjJgCsglFuzzyList2Command", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String fuzzyList2(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgCsgl/fuzzy_b", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sjJgCsglInsertGroup", commandKey = "sjJgCsglInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgCsgl/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "sjJgCsglUpdateGroup", commandKey = "sjJgCsglUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgCsgl/edit", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "disableFallback", groupKey = "sjJgCsglDisableGroup", commandKey = "sjJgCsglDisableCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String disable(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgCsgl/cancel", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "examineFallback", groupKey = "sjJgCsglExamineGroup", commandKey = "sjJgCsglExamineCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String examine(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgCsgl/examine", vars, String.class);
    }

    private String fuzzyListFallback(Map<String, Object> vars) {
        return "sjJgCsglFail";
    }

    private String fuzzyList2Fallback(Map<String, Object> vars) {
        return "sjJgCsglFailB";
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

    private String examineFallback(Map<String, Object> vars) {
        return "fail";
    }
}
