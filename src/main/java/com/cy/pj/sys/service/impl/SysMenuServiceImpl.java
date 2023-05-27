package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
import com.cy.pj.sys.vo.SysUserMenuVo;
@Service
public class SysMenuServiceImpl implements SysMenuService{
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Override
	public List<SysMenu> findObjects() {
		List<SysMenu> list = sysMenuDao.findObjects();
		if(list==null||list.size()==0) 
		{
			throw new ServiceException("没有对应的菜单信息");
		}
		return list;
	}
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<=0)
			throw new ServiceException("参数值无效");
		//2.统计菜单子元素并校验
		int counts=sysMenuDao.getChildCount(id);
		if(counts>0)
			throw new ServiceException("请先删除子菜单");
		//3.删除角色菜单关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//4.删除自身信息
		int rows = sysMenuDao.deleteObject(id);
		if(rows==0) 
			throw new ServiceException("此菜单记录可能已经不存在");
		//5.返回结果
		return rows;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
	@Override
	public int saveObject(SysMenu entity) {
		//1.合法验证
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("菜单名不能为空");
		int rows;
		//2.保存数据
		try{
			rows=sysMenuDao.insertObject(entity);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		//3.返回数据
		return rows;
	}
	@Override
	public int updateObject(SysMenu entity) {
		//1.验证合法性
		if(entity==null) 
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new ServiceException("菜单名不能为空");
		int rows = sysMenuDao.updateObject(entity);
		if(rows==0)
			throw new ServiceException("记录可能已经不存在");
		return rows;
	}
	@Override
	public List<SysUserMenuVo> findUserMenusByUserId(Integer id) {
		//1.对用户id进行判断
		//2.基于用户id查找用户对应的角色id
		List<Integer> roleIds=sysUserDao.findRoleIdsByUserId(id);
		//3.基于角色id获取角色对应的菜单信息,并进行封装.
		List<Integer> menuIds=sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(new Integer[] {}));
		//4.基于菜单id获取用户对应的菜单信息并返回
		return sysMenuDao.findMenusByIds(menuIds);

	}

}
