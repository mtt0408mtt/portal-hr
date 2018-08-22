package com.pm.portal.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.pm.portal.validator.IsCode;


public class LoginVo {
	@IsCode
	@NotEmpty
	private String code;
	
	@NotEmpty
	@Length(min=32)
	private String password;
	
//	@NotEmpty
//	private String group;
//	@NotEmpty
//	private String agency;
//	@NotEmpty
//	private String department;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginVo [code=" + code + ", password=" + password + "]";
	}

//	@Override
//	public String toString() {
//		return "LoginVo [code=" + code + ", password=" + password + ", group=" + group + ", agency=" + agency
//				+ ", department=" + department + "]";
//	}


	
}
