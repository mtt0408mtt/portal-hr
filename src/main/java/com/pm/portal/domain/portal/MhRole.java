package com.pm.portal.domain.portal;


import lombok.Data;





@Data
public class MhRole {
	private Long id;
	private String name;
	private String comment;
	private String status;
	private Long updateTime;
	private Long createTime;
	private Long deleteTime;
	private String operator;
	private Long operatorId;
	private String roleAll;
	private Long agencyIdR;
	private Long groupIdR;
	private String agencyNameR;
	private String groupNameR;
	

}
