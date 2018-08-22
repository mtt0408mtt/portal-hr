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
public class SbArchiveClient {

    private final RestTemplate restTpl;

    @Autowired
    public SbArchiveClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String list(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbArchive/list",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "getFallBack", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String getArchive(Long id) {
        return restTpl.postForObject("http://device/device/sbArchive/getArchive",id,String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveArchive(Map<String,Object> vars) {
        return restTpl.postForObject("http://device/device/sbArchive/saveArchive",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "deldeteFallback", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String deleteArchive(Long id) {
        return restTpl.postForObject("http://device/device/sbArchive/delete",id,String.class);
    }

    @HystrixCommand(fallbackMethod = "saveBatchFailBack", groupKey = "sbArchiveGroup", commandKey = "sbArchiveCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveArchiveCerts(Object object) {
        return restTpl.postForObject("http://device/device/sbArchive/saveArchiveCerts",object,String.class);
    }

    private String saveBatchFailBack(Object object){return "fail";}

    private String getFallBack(Long id){return "fail";}

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
