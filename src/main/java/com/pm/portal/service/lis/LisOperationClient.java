package com.pm.portal.service.lis;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name ="pm-zuul-gateway",fallback = LisOperationFallback.class)
public interface LisOperationClient {
	
	@RequestMapping(method=RequestMethod.POST,value="/lis-service/lis/lis_operation_save")
	String save(Map<String, Object> vars);
	
	
	
//	@RequestMapping(method = RequestMethod.GET, value="/hello/{name}")
//	String hello(@PathVariable("name") String name);
//	
//	
//	@RequestMapping(method = RequestMethod.GET, value="/call/{id}")
//	Police getPolice(@PathVariable("id") Integer id);
	
//自定义feign翻译器注解	
//	@MyUrl(url = "/hellowd", method = "GET")
//	String myHello();

}
