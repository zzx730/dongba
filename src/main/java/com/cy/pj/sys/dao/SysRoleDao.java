package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.common.pojo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;

@Mapper
public interface SysRoleDao {
	int getRowCount(String name);
	List<SysRole> findPageObjects(String  name,Integer startIndex,Integer pageSize);
	@Delete("delete from sys_roles where id = #{id}")
	int deleteObject(Integer id);
	int insertObject(SysRole entity);
	SysRoleMenuVo findObjectById(Integer id);
	@Update("update sys_roles set name=#{name},note=#{note},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	int updateObject(SysRole entity);
	
	@Select("select count(id) from sys_roles where name = #{name}")
	int findObjectsByName(String name);
}
