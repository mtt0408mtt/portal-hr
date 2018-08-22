package com.pm.portal.service.sjhc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SjJgKchwClient {
    private final RestTemplate restTpl;

    @Autowired
    public SjJgKchwClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sjJgKchwListGroup", commandKey = "sjJgKchwListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgKchw/list", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "reportFallback", groupKey = "sjJgKchwReportGroup", commandKey = "sjJgKchwReportCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String report(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgKchw/report", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "report2Fallback", groupKey = "sjJgKchwReport2Group", commandKey = "sjJgKchwReport2Command", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String report2(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgKchw/report2", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "warnListFallback", groupKey = "sjJgKchwWarnListGroup", commandKey = "sjJgKchwWarnListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String warnList(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgKchw/warnList", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "resolveFallback", groupKey = "sjJgKchwResolveGroup", commandKey = "sjJgKchwResolveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String resolve(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgKchw/resolve", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "beforeResolveFallback", groupKey = "sjJgKchwBeforeResolveGroup", commandKey = "sjJgKchwBeforeResolveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String beforeResolve(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgKchw/beforeResolve", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "sjJgKchwUpdateGroup", commandKey = "sjJgKchwUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgKchw/edit", vars, String.class);
    }

    private String listFallback(Map<String, Object> vars) {
        return "sjJgKchwFail";
    }

    private String reportFallback(Map<String, Object> vars) {
        return "sjJgKchwFailB";
    }

    private String report2Fallback(Map<String, Object> vars) {
        return "sjJgKchwFailC";
    }

    private String warnListFallback(Map<String, Object> vars) {
        return "sjJgKchwFailF";
    }

    private String resolveFallback(Map<String, Object> vars) {
        return "sjJgKchwFailD";
    }

    private String beforeResolveFallback(Map<String, Object> vars) {
        return "sjJgKchwFailE";
    }

    private String insertFallback(Map<String, Object> vars) {
        return "fail";
    }
}
