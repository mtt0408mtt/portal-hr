package com.pm.portal.service.act;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.portal.dao.IdmDao;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.result.PMDataGridResult;
import com.pm.portal.vo.MhAdminVo;

@Service
public class IdmClient {
	@Autowired
	private IdmDao idmDao;

	public PMDataGridResult<List<MhAdmin>> getUsers(Map<String, Object> vars) {
		
		PMDataGridResult<List<MhAdmin>> result = new PMDataGridResult<List<MhAdmin>>();
		result.setRows(idmDao.getUsers(vars));
		result.setTotal(idmDao.countUsers(vars));
		return result;
	}

	public void addUser(Map<String, Object> vars) {
		idmDao.addUser(vars);
		
	}

	public void updateUser(Map<String, Object> vars) {
		idmDao.updateUser(vars);
		
	}



}
