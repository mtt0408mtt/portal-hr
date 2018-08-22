package com.pm.portal.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.pm.portal.domain.sjhc.SjJgKcmx;

@Service
public class CacheService {

	@Autowired
	private RestTemplate restTpl;
	
	@CacheResult
	@HystrixCommand
	public SjJgKcmx cacheSjJgKcmx(Integer id) {
		System.out.println("调用 cacheSjJgKcmx 方法");
//		Member member = restTpl.getForObject(
//				"http://spring-hy-member/member/{id}", Member.class, id);
		return null;
	}
	
	@CacheResult
	@HystrixCommand(commandKey = "cacheKey")
	public String getCache(Integer id) {
		System.out.println("执行查询方法");
		return null;
	}
	
	@CacheRemove(commandKey = "cacheKey")
	@HystrixCommand
	public void removeCache(Integer id) {
		System.out.println("删除缓存方法");
	}
}
