package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.vo.SysUserMenuVo;

public interface SysMenuService {
	List<SysMenu> findObjects();
	//先删除角色菜单关系数据再删除自身信息
	int deleteObject(Integer id);
	List<Node> findZtreeMenuNodes();
	int saveObject(SysMenu entity);
	int updateObject(SysMenu entity);
	List<SysUserMenuVo> findUserMenusByUserId(Integer id);
}
