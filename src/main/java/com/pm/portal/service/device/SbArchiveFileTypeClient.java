package com.pm.portal.service.device;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by User on 2018/8/10.
 */
@Service
public class SbArchiveFileTypeClient {

    private final RestTemplate restTpl;

    @Autowired
    public SbArchiveFileTypeClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbTypeGroup", commandKey = "sbTypeCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbArchiveFileType/list",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sbTypeGroup", commandKey = "sbTypeCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveOrUpdate(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbArchiveFileType/saveOrUpdate",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "deldeteFallback", groupKey = "sbTypeGroup", commandKey = "sbTypeCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String delete(Long id) {
        return restTpl.postForObject("http://device/device/sbArchiveFileType/delete",id,String.class);
    }



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
