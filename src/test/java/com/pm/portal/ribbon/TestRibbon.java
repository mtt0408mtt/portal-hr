package com.pm.portal.ribbon;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.niws.client.http.RestClient;
//使用自己的负载均衡规则来调用
public class TestRibbon {

	public static void main(String[] args) throws Exception {
		ConfigurationManager.getConfigInstance().setProperty(
	      		"my-client.ribbon.listOfServers", "localhost:8083,localhost:8084"); //在report-lis
		ConfigurationManager.getConfigInstance().setProperty(
	      		"my-client.ribbon.NFLoadBalancerRuleClassName", MyRule.class.getName());
		
		// 获取REST请求客户端
		RestClient client = (RestClient) ClientFactory
				.getNamedClient("my-client");
		// 创建请求实例
		HttpRequest request = HttpRequest.newBuilder().uri("/person").build();
		// 发 送10次请求到服务器中
		for (int i = 0; i < 10; i++) {
			HttpResponse response = client.executeWithLoadBalancer(request);
			String result = response.getEntity(String.class);
			System.out.println(result);
		}
	}

}
