package com.pm.portal.service.sjhc;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SjGyDmzdClient {
    private final RestTemplate restTpl;

    @Autowired
    public SjGyDmzdClient(RestTemplate restTpl) {
        this.restTpl = restTpl;
    }

    @HystrixCommand(fallbackMethod = "categoryListFallback", groupKey = "sjGyDmzdCategoryListGroup", commandKey = "sjGyDmzdCategoryListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String categoryList(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjGyDmzd/list_a", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "itemListFallback", groupKey = "sjGyDmzdItemListGroup", commandKey = "sjGyDmzdItemListCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String itemList(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjGyDmzd/list_b", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "itemList2Fallback", groupKey = "sjGyDmzdItemList2Group", commandKey = "sjGyDmzdItemList2Command", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String itemList2(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjGyDmzd/list_c", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "insertFallback", groupKey = "sjGyDmzdInsertGroup", commandKey = "sjGyDmzdInsertCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String insert(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjGyDmzd/add", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "updateFallback", groupKey = "sjGyDmzdUpdateGroup", commandKey = "sjGyDmzdUpdateCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String update(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjGyDmzd/edit", vars, String.class);
    }

    @HystrixCommand(fallbackMethod = "disableFallback", groupKey = "sjGyDmzdDisableGroup", commandKey = "sjGyDmzdDisableCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")}, threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "5")})
    public String disable(Map<String, Object> vars) {
        return restTpl.postForObject("http://pm-zuul-gateway/sjhc/sjhc/sjGyDmzd/cancel", vars, String.class);
    }

    private String categoryListFallback(Map<String, Object> vars) {
        return "sjGyDmzdFailA";
    }

    private String itemListFallback(Map<String, Object> vars) {
        return "sjGyDmzdFailB";
    }

    private String itemList2Fallback(Map<String, Object> vars) {
        return "sjGyDmzdFailC";
    }

    private String insertFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String updateFallback(Map<String, Object> vars) {
        return "fail";
    }

    private String disableFallback(Map<String, Object> vars) {
        return "fail";
    }

}
