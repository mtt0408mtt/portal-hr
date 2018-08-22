package com.pm.portal.feign.contract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import feign.Contract.BaseContract;
import feign.MethodMetadata;
//提供一个翻译器告诉feign
public class MyContract extends BaseContract {

	@Override
	protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processAnnotationOnMethod(MethodMetadata data,
			Annotation annotation, Method method) {
		// 注解是MyUrl类型的，才处理
		if(MyUrl.class.isInstance(annotation)) {
			MyUrl myUrl = method.getAnnotation(MyUrl.class);
			String url = myUrl.url();
			String httpMethod = myUrl.method();
			data.template().method(httpMethod);
			data.template().append(url);
		}
	}

	@Override
	protected boolean processAnnotationsOnParameter(MethodMetadata data,
			Annotation[] annotations, int paramIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
