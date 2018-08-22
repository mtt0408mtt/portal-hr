package com.pm.portal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pm.portal.controller.demo.VPermission;
import com.pm.portal.domain.portal.MhAdmin;
import com.pm.portal.domain.portal.MhAgency;
import com.pm.portal.domain.portal.MhDepartment;
import com.pm.portal.domain.portal.MhGroup;
import com.pm.portal.vo.MhAdminVo;


@Mapper	
public interface MhAdminDao {

	@Select("select * from ACT_ID_USER where id_ = #{id}")
	public MhAdmin getById(@Param("id") long id);

	@Update("update ACT_ID_USER set pwd_ = #{pwd_} where id_ = #{id_}")
	public void update(MhAdmin toBeUpdate);

	@Select("select * from ACT_ID_USER admin")
	public List<MhAdminVo> listAdminsVo();
	
//	@Update("update ACT_ID_USER set  last_login_time= #{lastLoginTime} where code = #{code}")
//	public void updateLastLoginTime(MhAdmin admin);
	
	@Select("select count(1) from ACT_ID_USER")
	public long countAdminsVO();

	@Select("select * from ACT_ID_USER where id_ = #{id_}")
	public MhAdmin getByCode(@Param("id_") String code);
	
	@Select("select * from mh_group where status='1'")
	public List<MhGroup> listGroup();
	
	@Select("select * from mh_agency where status='1' and group_id=#{group_id} and type='1'")
	public List<MhAgency> listAgency(@Param("group_id") long group_id);
	
	@Select("select * from mh_department where status='1' and agency_id=#{agency_id}")
	public List<MhDepartment> listDepartment(@Param("agency_id") long agency_id);
	
	
	@Select("select distinct subsys_id,sub_home,sub_name from v_permission where admin_code =#{admin.code} and status='1' "
			+ "and group_id=#{admin.area.currentGroupId} and agency_id=#{admin.area.currentAgencyId}")
	public List<VPermission> listSubs(Map<String, Object> vars );


	
	@Select("select count(1) from  v_permission where admin_code =#{code} and agency_id=#{agencyId} and depart_id=#{departmentId} and group_id=#{groupId}")
	public Integer haveRoleForGroup(Map<String, Object> vars);
	
	
	
	
}
