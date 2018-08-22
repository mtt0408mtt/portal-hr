package com.pm.portal.controller.sjhc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.pm.portal.domain.sjhc.SjJgKcmx;
import com.pm.portal.service.sjhc.SjJgKcmxService2;

@RestController
@RequestMapping(value = "/sjhc/kcmx2")
public class KcmxController2 {
	
	@Autowired
	private SjJgKcmxService2 sjJgKcmxService2;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public SjJgKcmx getSjJgKcmx() {
		HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory
				.getInstance(HystrixCommandKey.Factory
						.asKey("sjJgKcmxService2#getSjJgKcmx()"));	
		System.out.println("断路器状态：" + breaker.isOpen());
		return sjJgKcmxService2.getSjJgKcmx(1);
	}

}
