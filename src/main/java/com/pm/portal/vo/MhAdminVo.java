package com.pm.portal.vo;

import java.util.Date;
import java.util.List;

import com.pm.portal.domain.portal.MhAgency;
import com.pm.portal.domain.portal.MhDepartment;
import com.pm.portal.domain.portal.MhDuty;
import com.pm.portal.domain.portal.MhGroup;

import lombok.Data;

@Data
public class MhAdminVo {
	private Long id;
	private String name;

	private int status;
	private String code;
	private String operator;
	private Long operatorId;
	private Long registerTime;
	private Long lastLoginTime;
	private String head;
	private Long currentAgencyId;
	private String currentAgencyName;
	private Long currentDepartmentId;
	private String currentDepartmentName;
	private Long currentDutyId;
	private String currentDutyName;
	private Long currentGroupId;
	private String currentGroupName;
	
	

}
