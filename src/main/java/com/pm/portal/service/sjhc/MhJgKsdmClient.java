package com.pm.portal.service.sjhc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MhJgKsdmClient {
    private final RestTemplate restTpl;

    @Autowired
    public MhJgKsdmClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "mhJgKsdmListGroup", commandKey = "mhJgKsdmListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/mhJgKsdm/list", vars, String.class);
    }

    private String listFallback(Map<String, Object> vars) {
        return "mhJgKsdmFail";
    }
}
