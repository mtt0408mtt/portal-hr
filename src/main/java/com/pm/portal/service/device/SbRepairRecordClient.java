package com.pm.portal.service.device;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 2018/8/6.
 */
@Service
public class SbRepairRecordClient {

    private final RestTemplate restTpl;

    @Autowired
    public SbRepairRecordClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "listFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String listRepairRecord(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbRepairRecord/list",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveRepairRecord(Map<String, Object> vars) {
        return restTpl.postForObject("http://device/device/sbRepairRecord/saveOrUpdateRecord",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String deleteRepairRecord(Long repairId) {
        return restTpl.postForObject("http://device/device/sbRepairRecord/deleteRecord",repairId,String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String getCostsList(Long repairId) {
        return restTpl.postForObject("http://device/device/sbRepairRecord/getCostsList",repairId,String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String saveOrUpdateCosts(Object vars) {
        return restTpl.postForObject("http://device/device/sbRepairRecord/saveOrUpdateCosts",vars,String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String deleteCost(Long costsId) {
        return restTpl.postForObject("http://device/device/sbRepairRecord/deleteCost",costsId,String.class);
    }

    @HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "sbSysParamGroup", commandKey = "sbSysParamCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String getCurrentRepairRecord(Long repairId) {
        return restTpl.postForObject("http://device/device/sbRepairRecord/getCurrentRepairRecord",repairId,String.class);
    }


    private String findParamsFallback(List<String> idss){return "fail";}

    private String getFallback(){return "fail";};

    private String deleteFallback(Long repairId) {
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
