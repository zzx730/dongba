package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface SysRoleMenuDao {
	/**
	 * 基于菜单id删除角色菜单关系数据
	 */
	@Delete("delete from sys_role_menus where menu_id = #{menuId}")
	int deleteObjectsByMenuId(Integer menuId);
	@Delete("delete from sys_role_menus where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);
	int insertObjects(Integer roleId,Integer[] menuIds);
	int findMenuIdsByRoleId(Integer roleId);
	List<Integer> findMenuIdsByRoleIds(Integer[] roleIds);
	
}
