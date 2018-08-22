package com.pm.portal.service.sjhc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pm.portal.domain.sjhc.SjJgKcmx;

@Service
public class SjJgKcmxService {
	
	@Autowired
	private RestTemplate restTpl;
	//https://github.com/Netflix/Hystrix/wiki/Configuration
	@HystrixCommand(fallbackMethod = "getSjJgKcmxFallback", groupKey = "SjJgKcmxGroup", commandKey = "SjJgKcmxCommandKey", 
			commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
	}, threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "5")
	})
	public SjJgKcmx getSjJgKcmx(Integer id) {
		try {
			Thread.sleep(2000);//让他出错
		} catch (Exception e) {
			
		}
		
		SjJgKcmx kcmx = restTpl.getForObject(
				"http://sjhc/sjJgKcmx/{id}", SjJgKcmx.class, id);
		return kcmx;
	}

	public SjJgKcmx getSjJgKcmxFallback(Integer id) {
		SjJgKcmx m = new SjJgKcmx();
		m.setId(1);
		m.setComment("error SjJgKcmx");
		return m;
	}

}
