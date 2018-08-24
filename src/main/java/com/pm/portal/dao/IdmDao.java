package com.pm.portal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pm.portal.domain.portal.MhAdmin;

@Mapper	
public interface IdmDao {
	
	@Select("<script>select * from ACT_ID_USER where 1=1 <if test='filter!=null'> and upper(first_) like '%${filter}%'</if> order by ${sort} limit ${startIndex},${pageSize}</script>")
	public List<MhAdmin> getUsers(Map<String, Object> vars);

	@Select("<script>select count(1) from ACT_ID_USER where 1=1 <if test='filter!=null'> and upper(first_) like '%${filter}%'</if> </script>")
	public long countUsers(Map<String, Object> vars);

	
	@Insert("insert into ACT_ID_USER(id_, first_, last_, email_,pwd_)values(#{id},#{firstName},#{lastName},#{email},#{password})")
	public void addUser(Map<String, Object> vars);

}
