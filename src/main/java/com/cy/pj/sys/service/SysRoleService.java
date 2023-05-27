package com.cy.pj.sys.service;


import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.common.pojo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;

public interface SysRoleService {
	PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
	int deleteObject(Integer id);
	int saveObject(SysRole entity,Integer[] menuIds);
	SysRoleMenuVo findObjectById(Integer id);
	int updateObject(SysRole entity,Integer[] menuIds);
	String findObjectsByName(String name);
}
