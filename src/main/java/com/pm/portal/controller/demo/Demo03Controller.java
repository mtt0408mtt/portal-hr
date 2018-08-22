package com.pm.portal.controller.demo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
/**
 * 
 * 
 * 
 * @author 精准医疗
 *
 */
@RestController
public class Demo03Controller {
	
	//testclient 测试负载均衡 这个一般放在服务提供者
	@RequestMapping(value = "/call/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Police call(@PathVariable Integer id, HttpServletRequest request) {
		Police p = new Police();
		p.setId(id);
		p.setName("angus");
		p.setMessage(request.getRequestURL().toString());//查看
		return p;
	}
	
	//客户端取服务端抓取注册表
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("/meta")
	@ResponseBody
	public String getMetadata() {
		List<ServiceInstance> instances = discoveryClient.getInstances("report-lis-service"); //服务的实例信息
		for(ServiceInstance ins : instances) {
			String name = ins.getMetadata().get("company-name");
			System.out.println(ins.getPort() + "---" + name);//8083---mtt
		}
		return "";
	}
	
	@GetMapping("/list")
	@ResponseBody
	public String serviceCount() {
		//report-lis-service: 1 获取服务数量
		//portal: 1
		List<String> names = discoveryClient.getServices();
		for(String serviceId : names) {
			List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
			System.out.println(serviceId + ": " + instances.size());
		}
		return "";
	}
	
	//spring内置的均衡器 返回的是服务实例
	@Autowired
	private LoadBalancerClient client;

	@RequestMapping(value = "/lb", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceInstance lb() {
		ServiceInstance si = client.choose("spring-lb-provider");
		return si;
	}
	
	//这个是给我用来创建客户端的
	@Autowired
	private SpringClientFactory factory;
	
	@RequestMapping(value = "/fa", method = RequestMethod.GET)
	public String factory() {
		ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer)factory.getLoadBalancer("default"); //使用default的负载均衡器 //默认使用的是这个ZoneAwareLoadBalancer 负载均衡器
		System.out.println(lb.getRule().getClass().getName()); //查看zone的规则
		
		ZoneAwareLoadBalancer lb2 = (ZoneAwareLoadBalancer)factory.getLoadBalancer("spring-lb-provider");
		System.out.println(lb2.getRule().getClass().getName()); //这里最终输出的是myrule
		return "";
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/toHello")
	public String toHello() {
		//String result = helloClient.toHello();
		HystrixCircuitBreaker breaker = HystrixCircuitBreaker.Factory
				.getInstance(HystrixCommandKey.Factory
						.asKey("HelloClient#toHello()"));	
		System.out.println("断路器状态：" + breaker.isOpen());//获取断路器的状态
		return null;
	}

}

 class Police {

	private Integer id;
	private String name;
	private String message;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
