package com.pm.portal.service.device;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by User on 2018/7/26.
 */
@Service
public class SbDicClient {

    private final RestTemplate restTpl;

    @Autowired
    public SbDicClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbDicGroup", commandKey = "sbDicCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbDic/list",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sbDicGroup", commandKey = "sbDicCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveOrUpdate(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbDic/saveOrUpdate",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "getDicByTypeFallBack", groupKey = "sbDicGroup", commandKey = "sbDicCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String getDicByType(String dic_type) {
        return restTpl.postForObject("http://device/device/sbDic/getDicByType",dic_type,String.class);
    }

    private String getDicByTypeFallBack(String dic_type){return "fail";}

    private String deldeteFallback(Long id) {
        return "fail";
    }

    private String listFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String insertFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String updateFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String getFactoryPropertyFallback(){return "fail";}
}
