package com.pm.portal.controller.sjhc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pm.portal.domain.sjhc.SjJgKcmx;
import com.pm.portal.service.sjhc.SjJgKcmxService;


@RestController
@RequestMapping(value = "/sjhc/kcmx")
public class KcmxController {
	
	@Autowired
	private SjJgKcmxService sjJgKcmxService;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public SjJgKcmx getSjJgKcmx() {
		return sjJgKcmxService.getSjJgKcmx(1);
	}

}
