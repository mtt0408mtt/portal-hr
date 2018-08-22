package com.pm.portal.service.device;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by User on 2018/7/18.
 */
@Service
public class SbFactoryClient {

    private final RestTemplate restTpl;

    @Autowired
    public SbFactoryClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbFactoryGroup", commandKey = "sbFactoryCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbFactory/list",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sbFactoryGroup", commandKey = "sbFactoryCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveFactory(Object vars) {
        return restTpl.postForObject("http://device/device/sbFactory/saveFactory",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "getFactoryPropertyFallback", groupKey = "sbFactoryGroup", commandKey = "sbFactoryCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String getFactoryProperty() {
        return restTpl.postForObject("http://device/device/sbFactory/getFactoryProperty",null,String.class);
    }

    @HystrixCommand(fallbackMethod = "deldeteFallback", groupKey = "sbFactoryGroup", commandKey = "sbFactoryCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String deleteFactory(Long id) {
        return restTpl.postForObject("http://device/device/sbFactory/deleteFactory",id,String.class);
    }

    @HystrixCommand(fallbackMethod = "getFactoriesFallback", groupKey = "sbFactoryGroup", commandKey = "sbFactoryCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String getFactoriesByType(Integer[] ids) {
        return restTpl.postForObject("http://device/device/sbFactory/getFactoriesByType",ids,String.class);
    }

    private String getFactoriesFallback(Integer[] ids){return "fail";}

    private String deldeteFallback(Long id) {
        return "fail";
    }

    private String listFallback(Map<String, Object> vars) {
        return "sbFactoryFail";
    }

    private String insertFallback(Object vars) {
        return "fail";
    }

    private String updateFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String getFactoryPropertyFallback(){return "fail";}
}
