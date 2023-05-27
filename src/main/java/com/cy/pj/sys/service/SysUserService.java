package com.cy.pj.sys.service;


import java.util.Map;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.common.pojo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;

public interface SysUserService {
	PageObject<SysUserDeptVo> findPageObjects(String username,Integer pageCurrent);
	int validById(Integer id,Integer valid);
	int saveObject(SysUser entity,Integer[] roleIds);
	int updateObject(SysUser entity,Integer[] roleIds);
	Map<String,Object> findObjectById(Integer id);
	int updatePassword(String password,String newPassword,String cfgPassword);
}
