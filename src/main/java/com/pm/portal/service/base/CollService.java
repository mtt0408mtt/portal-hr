package com.pm.portal.service.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pm.portal.domain.sjhc.SjJgKcmx;

@Service
public class CollService {

	@HystrixCollapser(batchMethod = "getSjJgKcmxs", collapserProperties = {
			@HystrixProperty(name = "timerDelayInMilliseconds", value = "1000")
	})
	public Future<SjJgKcmx> getSjJgKcmx(Integer id) {
		System.out.println("执行单个查询的方法");
		return null;
	}
	
	@HystrixCommand
	public List<SjJgKcmx> getSjJgKcmxs(List<Integer> ids) {
		List<SjJgKcmx> mems = new ArrayList<SjJgKcmx>();
		for(Integer id : ids) {
			System.out.println(id);
			SjJgKcmx m = new SjJgKcmx();
			m.setId(id);
			m.setComment("angus");
			mems.add(m);
		}
		return mems;
	}
}
