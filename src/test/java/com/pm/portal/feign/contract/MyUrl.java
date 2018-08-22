package com.pm.portal.feign.contract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//Feign解析第三方注解
@Target(ElementType.METHOD)//只能修饰方法
@Retention(RetentionPolicy.RUNTIME)
public @interface MyUrl {

	String url();
	String method();
}
