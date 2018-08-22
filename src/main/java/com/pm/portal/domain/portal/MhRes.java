package com.pm.portal.domain.portal;

import lombok.Data;


@Data
public class MhRes {
	private Long id;
	private String name;
	private String resUrl;
	private String comment;
	private String status;
	private Long updateTime;
	private Long createTime;
	private Long deleteTime;
	private String operator;
	private Long operatorId;
	
	private String resAll;
	
	private Long subsysId;
	private Long agencyId;
	private Long departId;
	private Long groupId;
	
	private String subsysName;
	private String agencyName;
	private String departName;
	private String groupName;
	

}


