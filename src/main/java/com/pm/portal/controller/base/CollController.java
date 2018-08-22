package com.pm.portal.controller.base;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.domain.sjhc.SjJgKcmx;
import com.pm.portal.service.base.CollService;


@RestController
public class CollController {
	
	@Autowired
	private CollService collService;

	@RequestMapping(value = "/coll", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String testCollapse() throws Exception {
		// 连续执行3次请求
		Future<SjJgKcmx> f1 = collService.getSjJgKcmx(1);
		Future<SjJgKcmx> f2 = collService.getSjJgKcmx(2);
		Future<SjJgKcmx> f3 = collService.getSjJgKcmx(3);
		SjJgKcmx p1 = f1.get();
		SjJgKcmx p2 = f2.get();
		SjJgKcmx p3 = f3.get();	
		return "";
	}
}
