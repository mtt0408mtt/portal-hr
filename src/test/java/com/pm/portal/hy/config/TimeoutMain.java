package com.pm.portal.hy.config;

import com.netflix.config.ConfigurationManager;

public class TimeoutMain {

	public static void main(String[] args) {
		TimeoutCommand c = new TimeoutCommand();
		String result = c.execute();
		System.out.println(result);


	}

	
}
