package com.pm.portal.domain.portal;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class MhGroup {
	private Long id;
	
	private String name;
	@JSONField(serialize=false)  
	private String comment;
	@JSONField(serialize=false)  
	private String status;
	
	private String code;
	private List<MhAgency> agency;
	

}
