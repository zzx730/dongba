package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.common.pojo.SysRoleMenuVo;
import com.cy.pj.common.util.AssertUtil;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public PageObject<SysRole> findPageObjects(String name,Integer pageCurrent) {
		//1.校验参数
		AssertUtil.isArgumentValid(pageCurrent<1||pageCurrent==null, "当前页码值无效");
		//2.查询总记录数并进行校验
		int rowCount = sysRoleDao.getRowCount(name);
		AssertUtil.isResultValid(rowCount==0, "没有找到对应记录");
		//if(rowCount==0)
			//throw new ServiceException("没有找到对应记录");
		//3.查看当前页记录
		int pageSize=2;
		int startIndex = (pageCurrent-1) * pageSize;
		List<SysRole> records = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		return new PageObject<>(pageCurrent,pageSize,rowCount,records);
	}
	@Override
	public int deleteObject(Integer id) {
		//1.验证数据合法性
		if(id==null||id<=0)
			throw new IllegalArgumentException("请先选择");
		//2.基于id删除关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//3.删除角色自身
		int rows = sysRoleDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("此记录可能已经不存在");
		return rows;
	}
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.验证参数合法性
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不允许为空");
		if(menuIds==null||menuIds.length==0)
			throw new IllegalArgumentException("必须为角色分配权限");
		//2.保存角色自身信息
		int rows;
		try {
			rows = sysRoleDao.insertObject(entity);
		} catch (Exception e) {
			throw new ServiceException("保存失败");
		}
		//3.保存角色菜单关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回业务结果
		return rows;
	}
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new IllegalArgumentException("id的值不合法");
		//2.执行查询
		SysRoleMenuVo result=sysRoleDao.findObjectById(id);
		if(result==null)
			throw new ServiceException("此记录可能已经不存在");
		return result;
	}
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.检验参数合法性
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(entity.getId()==0)
			throw new IllegalArgumentException("id值不能为空");
		if(StringUtils.isEmpty(entity.getName()))
			throw new IllegalArgumentException("角色名不能为空");
		if(menuIds.length==0||menuIds==null)
			throw new IllegalArgumentException("必须为角色指定一个权限");
		//2.更新数据
		int rows = sysRoleDao.updateObject(entity);
		if(rows == 0)
			throw new ServiceException("对象可能已经不存在");
		sysRoleMenuDao.deleteObjectsByMenuId(entity.getId());
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//3.返回结果
		return rows;
	}
	@Override
	public String findObjectsByName(String name) {
		//1.检验参数
		AssertUtil.isArgumentValid(name==null||name.length()<=0,"角色名不能为空");
		//2.执行查询语句
		int rows = sysRoleDao.findObjectsByName(name);
		if(rows>1) {
			return "ok";
			}
		//log.info("存在"+rows+"条角色名重复记录");
		if(rows==1)return "1";
		return "no";
	}

}
