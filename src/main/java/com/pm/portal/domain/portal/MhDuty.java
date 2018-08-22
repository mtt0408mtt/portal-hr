package com.pm.portal.domain.portal;

import lombok.Data;

@Data
public class MhDuty {
	private Long id;
	private String name;
	private String comment;
	private String status;
	private MhDepartment department;

}
