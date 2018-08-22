package com.pm.portal.feign;


import feign.Headers;
import feign.Param;
import feign.RequestLine;
//服务端的见MyRestController这个类 我挪过来了
public interface HelloClient {

	@RequestLine("GET /hello")
	public String hello();
	
	@RequestLine("GET /person/{id}")
	public Person getPerson(@Param("id") Integer id);

	@RequestLine("POST /person/create")
	@Headers("Content-Type: application/json") //服务端只接受json的设置
	public String createPerson(Person p);
	
	@RequestLine("POST /person/createXML")
	@Headers("Content-Type: application/xml")
	public Result createXMLPerson(Person p);

}


class Person {

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