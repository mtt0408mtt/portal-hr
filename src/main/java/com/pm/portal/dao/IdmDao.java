package com.pm.portal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pm.portal.domain.portal.MhAdmin;

@Mapper	
public interface IdmDao {
	
	@Select("<script>select * from ACT_ID_USER where 1=1 <if test='filter!=null'> and upper(first_) like '%${filter}%'</if> order by ${sort} limit ${startIndex},${pageSize}</script>")
	public List<MhAdmin> getUsers(Map<String, Object> vars);

	@Select("<script>select count(1) from ACT_ID_USER where 1=1 <if test='filter!=null'> and upper(concat(first_,last_)) like '%${filter}%'</if> </script>")
	public long countUsers(Map<String, Object> vars);

	
	@Insert("insert into ACT_ID_USER(id_, first_, last_, email_,pwd_)values(#{id},#{firstName},#{lastName},#{email},#{password})")
	public void addUser(Map<String, Object> vars);
	
	@Update("update ACT_ID_USER set first_=#{first_},last_=#{last_}, email_ = #{email_} where id_ = #{id_}")
	public void updateUser(Map<String, Object> vars);

	@Update("<script>update ACT_ID_USER set pwd_=#{pwd_} where id_ in"
			+" <foreach item='item' index='index' collection='users' open='(' separator=',' close=')'>" 
			+" #{item}  </foreach> </script> ")
	public void updateUserPassword(Map<String, Object> vars);

	@Delete("<script>delete  from ACT_ID_USER where id_ =#{id_} </script>")
	public void deleteUser(Map<String, Object> vars);

}
