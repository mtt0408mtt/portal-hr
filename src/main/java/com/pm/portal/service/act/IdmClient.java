package com.pm.portal.service.act;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.portal.dao.IdmDao;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.domain.portal.MhGroup;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.PMDataGridResult;
import com.pm.portal.result.Result;
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

	public Result addUser(Map<String, Object> vars) {
		String id = (String) vars.get("id");
		vars.put("id_", id);
		MhAdmin user = idmDao.findUser(vars);
		if (user != null) {
			return Result.error(CodeMsg.MESSAGE03);
		}
		idmDao.addUser(vars);
		return Result.success(id);

	}

	public void updateUser(Map<String, Object> vars) {
		idmDao.updateUser(vars);

	}

	public void updateUserPassword(Map<String, Object> vars) {
		idmDao.updateUserPassword(vars);

	}

	public void deleteUser(Map<String, Object> vars) {
		idmDao.deleteUser(vars);

	}

	public List<MhGroup> getGroups(Map<String, Object> vars) {
		return idmDao.getGroups(vars);
	}

	public MhGroup getGroup(Map<String, Object> vars) {
		MhGroup group = idmDao.getGroup(vars);

		List<MhAdmin> groupUser = idmDao.getGroupUser(vars);
		group.setUsers(groupUser);

		return group;
	}

	public Result createGroup(Map<String, Object> vars) {
		String id_ = (String) vars.get("id_");
		vars.put("groupId", id_);
		MhGroup group = idmDao.getGroup(vars);
		if (group != null) {
			return Result.error(CodeMsg.MESSAGE02);
		}
		idmDao.createGroup(vars);
		return Result.success(id_);
	}

	public void updateGroup(Map<String, Object> vars) {
		idmDao.updateGroup(vars);
	}

	public void deleteGroup(Map<String, Object> vars) {
		idmDao.deleteGroup(vars);
		
	}

	public void deleteGroupmember(Map<String, Object> vars) {
		idmDao.deleteGroupmember(vars);
	}

}
