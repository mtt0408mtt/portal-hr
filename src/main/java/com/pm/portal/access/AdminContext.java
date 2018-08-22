package com.pm.portal.access;

import com.pm.portal.domain.portal.MhAdmin;

public class AdminContext {
	
	private static ThreadLocal<MhAdmin> adminHolder = new ThreadLocal<MhAdmin>();
	
	public static void setAdmin(MhAdmin admin) {
		adminHolder.set(admin);
	}
	
	public static MhAdmin getAdmin() {
		return adminHolder.get();
	}

}
