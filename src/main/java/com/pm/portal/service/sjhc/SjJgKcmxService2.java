package com.pm.portal.service.sjhc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pm.portal.domain.sjhc.SjJgKcmx;

@Service
public class SjJgKcmxService2 {
	
    //误删了
	public SjJgKcmx getSjJgKcmx(Integer id) {
		try {
			Thread.sleep(2000);//让他出错
		} catch (Exception e) {
			
		}
		
		SjJgKcmx kcmx =null;
		return kcmx;
	}

	public SjJgKcmx getSjJgKcmxFallback(Integer id) {
		SjJgKcmx m = new SjJgKcmx();
		m.setId(1);
		m.setComment("error SjJgKcmx");
		return m;
	}

}
