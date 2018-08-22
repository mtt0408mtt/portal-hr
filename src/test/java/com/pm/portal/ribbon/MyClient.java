package com.pm.portal.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
//myconfig 自定义配置负载均衡用到 spring cloud 整合ribbon
//调用这个spring-lb-provider提供者时 使用这个规则 或者配置文件中用default配置
//@RibbonClient(name = "spring-lb-provider", configuration = MyConfig.class)
public class MyClient {

}
