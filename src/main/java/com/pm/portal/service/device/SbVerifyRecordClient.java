package com.pm.portal.service.device;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created by User on 2018/7/31.
 */
@Service
public class SbVerifyRecordClient {

    private final RestTemplate restTpl;

    @Autowired
    public SbVerifyRecordClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbVerifyRecord/list",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "getFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String getVerifyList() {
        return restTpl.postForObject("http://device/device/sbVerify/getAll",null,String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveBatch(Object vars) {
        return restTpl.postForObject("http://device/device/sbVerifyRecord/saveOrUpdateBatch",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFileFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String updateFile(Map<String,String> vars) {
        return restTpl.postForObject("http://device/device/sbVerifyRecord/updateFile",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String listRecordView(Map<String,Object> vars) {
        return restTpl.postForObject("http://device/device/sbVerifyRecord/listRecordView",vars,String.class);
    }

    private String getFallback(){return "fail";};

    private String updateFileFallback(Map<String,String> vars){return "fail";}

    private String deldeteFallback(Long id) {
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

    private String getFactoryPropertyFallback(){return "fail";}
}
