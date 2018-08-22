package com.pm.portal.domain.portal;


import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class MhAgency {
	private Long id;
	private String name;
	@JSONField(serialize=false)  
	private String desc;
	@JSONField(serialize=false)  
	private String comment;
	@JSONField(serialize=false)  
	private String stauts;
	private String code;
	@JSONField(serialize=false)  
	private String address;
	@JSONField(serialize=false)  
	private String postCode;
	@JSONField(serialize=false)  
	private String contactName;
	@JSONField(serialize=false)  
	private String contactPhone;
	@JSONField(serialize=false)  
	private Long updateTime;
	@JSONField(serialize=false) 
	private Long createTime;
	@JSONField(serialize=false)  
	private Long deleteTime;
	@JSONField(serialize=false)  
	private String operator;
	@JSONField(serialize=false)  
	private Long operatorId;
	@JSONField(serialize=false)  
	private String logo;
	@JSONField(serialize=false)  
	private String type;
	@JSONField(serialize=false)  
	private String fax;
	@JSONField(serialize=false)  
	private String email;
	@JSONField(serialize=false)  
	private String groupId;
	@JSONField(serialize=false)  
	private String groupName;
	@JSONField(serialize=false)  
	private String agencyAll;
	
	private List<MhDepartment> department;
}





