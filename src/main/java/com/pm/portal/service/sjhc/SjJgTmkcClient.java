package com.pm.portal.service.sjhc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SjJgTmkcClient {
    private final RestTemplate restTpl;

    @Autowired
    public SjJgTmkcClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "openFallback", groupKey = "sjJgTmkcOpenGroup", commandKey = "sjJgTmkcOpenCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String open(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgTmkc/open", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "openCancelFallback", groupKey = "sjJgTmkcOpenCancelGroup", commandKey = "sjJgTmkcOpenCancelCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String openCancel(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjJgTmkc/openCancel", vars, String.class);
    }

    private String openFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String openCancelFallback(Map<String, Object> vars) {
        return "fail";
    }
}
