package com.pm.portal.domain.portal;


import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class MhDepartment {
	private Long id;
	private String name;
	private String code;
	@JSONField(serialize=false)  
	private Long depTypeId;
	@JSONField(serialize=false)  
	private String depTypeName;
	@JSONField(serialize=false)  
	private String comment;
	@JSONField(serialize=false)  
	private String status;
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
	
	private List<MhDuty> duty;

}
