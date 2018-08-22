package com.pm.portal.feign;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import feign.Client;
import feign.Request;
import feign.Request.Options;
import feign.Response;
//自定义feign客户端 用httpclient
public class MyClient implements Client {

	public Response execute(Request request, Options options)
			throws IOException {
		System.out.println("this is my client");
		try {
			// 创建一个默认的客户端
			CloseableHttpClient httpclient = HttpClients.createDefault();
			// 获取调用的HTTP方法
			final String method = request.method();
			// 创建一个HttpClient的HttpRequest
			HttpRequestBase httpRequest = new HttpRequestBase() {
				public String getMethod() {
					return method;
				}
			};
			// 设置请求地址
			httpRequest.setURI(new URI(request.url()));
			// 执行请求，获取响应
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// 获取响应的主体内容
			byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
			// 将HttpClient的响应对象转换为Feign的Response
			Response response = Response.builder()
					.body(body)
					.headers(new HashMap<String, Collection<String>>())
					.status(httpResponse.getStatusLine().getStatusCode())
					.build();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
