package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.common.pojo.CheckBox;
import com.cy.pj.common.pojo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysDept;
import com.cy.pj.sys.entity.SysUser;

@Mapper
public interface SysUserDao {
	int getRowCount( String username);
	List<SysUserDeptVo> findPageObjects(String  username,Integer startIndex,Integer pageSize);
	int validById(Integer id,Integer valid,String modifiedUser);
	int insertObject(SysUser entity);
	@Select("select id,name from sys_roles")
	List<CheckBox> findObjects();
	int insertObjects(Integer userId,Integer[] roleIds);
	
	@Update("update sys_users set username=#{username},email=#{email},mobile=#{mobile},deptId=#{deptId},modifiedTime=now(),modifiedUser=#{modifiedUser} where id=#{id}")
	int updateObject(SysUser entity);
	//角色id数据
	@Select("select role_id from sys_user_roles where user_id=#{userId}")
	List<Integer> findRoleIdsByUserId(Integer userId);
	@Select("select id,mobile,email,username,deptId from sys_users where id=#{id}")
	SysUserDeptVo findObjectById(Integer id);
	SysDept findObjectByDeptId(Integer id);
	@Delete("delete from sys_user_roles where user_id=#{id}")
	int deleteObjectByUserId(Integer userId);
	@Select("select * from sys_users where username=#{username}")
	SysUser findUserByUsername(String username);
	@Update("update sys_users set salt=#{newSalt},password=#{newPassword},modifiedTime=now() where id=#{id}")
	int updatePassword(String newPassword,String newSalt,Integer id);
	
	
}
