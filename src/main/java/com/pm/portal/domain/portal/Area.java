package com.pm.portal.domain.portal;

import lombok.Data;

@Data
public class Area {

	private String currentGroupId;
	private String currentGroupCode;
	private String currentAgencyId;
	private String currentAgencyCode;
	private String currentDepartmentId;
	private String currentDepartmentCode;
	@Override
	public String toString() {
		return this.currentGroupId+"bb"+this.currentGroupCode + "aa" + this.currentAgencyId +"bb"+this.currentAgencyCode+ "aa" + this.currentDepartmentId+"bb"+this.currentDepartmentCode;
	}

}
