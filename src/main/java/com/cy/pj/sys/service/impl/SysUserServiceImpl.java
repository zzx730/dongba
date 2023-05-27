package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.common.pojo.SysUserDeptVo;
import com.cy.pj.common.util.AssertUtil;
import com.cy.pj.common.util.ShiroUtils;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.entity.SysDept;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

import lombok.extern.slf4j.Slf4j;
@Transactional(timeout = 30,readOnly = false,isolation = Isolation.READ_COMMITTED,rollbackFor = Throwable.class,propagation = Propagation.REQUIRED)
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	//@RequiredCache
	@Transactional(readOnly = true)
	//对于spring框架而言，它的内部可以基于不同的模块创建不同的cache
	//Map<key,Cache>
	@Cacheable(value = "deptCache")
	@RequiredLog("分页查询")
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1.检验参数
		AssertUtil.isArgumentValid(pageCurrent==null||pageCurrent<=0,"当前页码值无效");
		//2.查询总记录数并校验
		int rowCount = sysUserDao.getRowCount(username);
		AssertUtil.isResultValid(rowCount==0, "没有查询到对应记录");
		int pageSize=3;
		int startIndex = (pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records= sysUserDao.findPageObjects(username, startIndex, pageSize);
		return new PageObject<>(pageCurrent,pageSize,rowCount,records);
	}
	
	@RequiredLog("用户状态")
	@Override
    public int validById(Integer id,Integer valid) {
            //1.合法性验证
            AssertUtil.isArgumentValid(id==null||id<=0,"参数不合法,id="+id);
            AssertUtil.isResultValid(valid==null||(valid!=1&&valid!=0), "参数不合法,valie="+valid);
            //if(StringUtils.isEmpty(modifiedUser))
            //throw new ServiceException("修改用户不能为空");
            //2.执行禁用或启用操作(admin为后续登陆用户）
            int rows=sysUserDao.validById(id, valid,"admin");
            //3.判定结果,并返回
            if(rows==0)
            throw new ServiceException("此记录可能已经不存在");
            return rows;
    }
	@RequiredLog("添加信息")
	@CacheEvict(value="deptCache",allEntries=true)
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		long start=System.currentTimeMillis();
        log.info("start:"+start);
		//1.检验参数
		AssertUtil.isArgumentValid(entity==null, "保存对象不能为空");
		AssertUtil.isArgumentValid(roleIds==null||roleIds.length==0, "需要为用户分配角色");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(entity.getUsername()), "用户名不能为空");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(entity.getPassword()), "密码不能为空");
		//2.保存自身信息
		//对密码进行加密
		String source = entity.getPassword();
		String salt = UUID.randomUUID().toString();
		SimpleHash sh = new SimpleHash("MD5",source,salt,1);//1.algorithmName 算法  2.原密码   3.盐值   4.hashIterations表示加密次数 Shiro框架  
		entity.setSalt(salt);
		entity.setPassword(sh.toHex());
		int rows = sysUserDao.insertObject(entity);
		AssertUtil.isResultValid(rows==0, "保存失败");
		//3.保存用户角色关系数据
		sysUserDao.insertObjects(entity.getId(), roleIds);
		long end=System.currentTimeMillis();
        log.info("end:"+end);
        log.info("total time :"+(end-start));
		return rows;
	}
	@RequiresPermissions("sys:user:update")
	@RequiredLog("修改信息")
	/**@CacheEvict
	 * value属性：用于指定要清除的缓存对象
	 * allEntries属性：表示要清除缓存对象中的哪些属性(true为所有)
	 * beforeInvocation = false表示修改之后清除缓存（默认），true就是修改之前清除缓存，也就是说不管执行结果如何都会清除缓存
	 */
	@CacheEvict(value="deptCache",allEntries=true,beforeInvocation = false)
	@Override
	public int updateObject(SysUser entity,Integer[] roleIds) {
		//1.检验参数
		AssertUtil.isArgumentValid(entity==null, "保存对象不能为空");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(entity.getUsername()), "用户名不能为空");
		AssertUtil.isArgumentValid(roleIds==null||roleIds.length==0, "必须为其指定角色");
		//2.更新用户数据
		int rows = sysUserDao.updateObject(entity);
		AssertUtil.isResultValid(rows==0, "记录可能已经不存在");
		//3.更新用户角色关系数据
		sysUserDao.deleteObjectByUserId(entity.getId());
		sysUserDao.insertObjects(entity.getId(),roleIds);
		return rows;
	}
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.检验参数
		AssertUtil.isArgumentValid(id==null||id<=0, "参数数据不合法,userId="+id);
		//获得回显数据
		SysUserDeptVo user =sysUserDao.findObjectById(id);
		SysDept sysDept = sysUserDao.findObjectByDeptId(id);
		user.setSysDept(sysDept);
		List<Integer> roleIds = sysUserDao.findRoleIdsByUserId(id);
		Map<String, Object> map = new HashedMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}

	@Override
	public int updatePassword(String password,String newPassword,String cfgPassword) {
		//1.参数检验
		AssertUtil.isArgumentValid(password==null, "原密码不能为空");//AssertUtil.isArgumentValid(StringUtils.isEmpty(), "原密码不能为空");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(newPassword), "新密码不能为空");
		AssertUtil.isArgumentValid(StringUtils.isEmpty(cfgPassword), "确认密码不能为空");
		AssertUtil.isArgumentValid(!(cfgPassword.equals(newPassword)), "俩次输入的密码不相等");
		SysUser user = ShiroUtils.getUser();//获取登录用户
		String newSalt = UUID.randomUUID().toString();
		SimpleHash sh = new SimpleHash("MD5",password,user.getSalt(),1);//判断原密码是否正确
		AssertUtil.isArgumentValid(!(sh.toHex().equals(user.getPassword())), "原密码不正确");
		sh = new SimpleHash("MD5",newPassword,newSalt,1);//对新密码进行加密
		int rows = sysUserDao.updatePassword(sh.toHex(),newSalt,user.getId());//将新密码更新到数据库
		if(rows==0) throw new ServiceException("修改失败");
		return rows;
	}

}
