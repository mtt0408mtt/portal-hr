package com.pm.portal.controller.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person getPerson(@PathVariable Integer id, HttpServletRequest request) {
		Person p = new Person();
		p.setId(id);
		p.setName("angus");
		p.setMessage(request.getRequestURL().toString());
		return p;
	}
	
	@RequestMapping(value = "/person/create", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public String createPerson(@RequestBody Person p) {
		System.out.println(p.getName() + "---" + p.getMessage());
		return "success, id: " + p.getId();
	}
	
	/**
	 * 参数与返回值均为XML
	 */
	@RequestMapping(value = "/person/createXML", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_XML_VALUE, 
			produces = MediaType.APPLICATION_XML_VALUE)
	public String createXMLPerson(@RequestBody Person person) {
		System.out.println(person.getName() + "-" + person.getId());
		return "<result><message>success</message></result>";
	}
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
