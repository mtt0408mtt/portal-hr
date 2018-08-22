package com.pm.portal.service.device;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 2018/8/3.
 */
@Service
public class SbRunRecordClient {

    private final RestTemplate restTpl;

    @Autowired
    public SbRunRecordClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbRunRecord/list",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveBatch(Object vars) {
        return restTpl.postForObject("http://device/device/sbRunRecord/saveBatch",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Long id) {
        return restTpl.postForObject("http://device/device/sbRunRecord/delete",id,String.class);
    }

    private String findParamsFallback(List<String> idss){return "fail";}

    private String getFallback(){return "fail";};

    private String deleteFallback(Long id) {
        return "fail";
    }

    private String listFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String insertFallback(Object vars) {
        return "fail";
    }

    private String updateFallback(Map<String, Object> vars) {
        return "fail";
    }

}
