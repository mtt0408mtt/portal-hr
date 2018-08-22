package com.pm.portal.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.service.base.CacheService;


@RestController
public class CacheController {
	
	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "/cache", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String cache() {
		for(int i = 0; i < 3; i++) {
			cacheService.cacheSjJgKcmx(1);
		}
		return "";
	}
	
	@RequestMapping(value = "/rc", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String testRemoveCache() {
		cacheService.getCache(1);
		cacheService.getCache(1);
		
		cacheService.removeCache(1);
		System.out.println("#########  分隔线   ###########");
		cacheService.getCache(1);
		return "";
	}
}
