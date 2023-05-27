package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.config.SpringAsyncConfig;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
@Service
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogDao sysLogDao;
	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		// TODO Auto-generated method stub
		//1.参数校验
		//1.1验证pageCurrent的合法性
		//不合法抛出IllegalArgumentException异常
		if(pageCurrent==null||pageCurrent<1) 
			throw new IllegalArgumentException("当前页码不正确");
		//2.基于用户名查询总记录数并校验
		//2.1执行查询
		int rowCount = sysLogDao.getRowCount(username);
		//2.2验证查询结果，加入结果为0不再执行如下操作

		if(rowCount==0) 
			//为了对业务中的信息进行更好的反馈和定位，通常会在项目中自定义异常
			//为了避免直接抛出RuntimeException
			throw new ServiceException("系统没有查到对应记录");
		//3.基于pageCurrent查询当前页记录（pageSize定义为3）
		//3.1定义pageSize
		int pageSize=3;//每页最多显示多少条记录
		//3.2计算startIndex
		int startIndex=(pageCurrent-1)*pageSize;
		//3.3执行当前数据的查询操作
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex,pageSize);
		//4.对查询结果进行封装并返回
		//4.1构建PageObject对象
		PageObject<SysLog> pageObject = new PageObject<>();
		//4.2封装数据
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setRecords(records);
		//		int pageCount = rowCount/pageSize;
		//		if(rowCount%pageSize!=0)pageCount++;
		//		pageObject.setPageCount(pageCount);
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		//5.返回封装结果
		return pageObject;


	}
	@Override
	public int getRowCount(String username) {
		return sysLogDao.getRowCount(username);
	}
	@Override
	public List<SysLog> findPageObjects(String username, Integer startIndex, Integer pageSize) {
		return sysLogDao.findPageObjects(username, startIndex, pageSize);
	}
	@Override
	public int deleteObjects(Integer... ids) {
		//1.参数校验
		if(ids==null||ids.length==0)
			throw new IllegalArgumentException("参数值无效");
		//2.执行删除
		int rows = sysLogDao.deleteObjects(ids);
		//进行结果校验
		if(rows == 0)
			throw new ServiceException("记录可能已经不存在");
		//返回结果
		return rows;
	}
	@Async("my-newThread")//使用此注解描述的方法会运行在由spring框架提供的一个线程
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void saveObject(SysLog entity) {
		System.out.println("SysLogServiceImpl.save:"+Thread.currentThread().getName());
		sysLogDao.insertObject(entity);
	}
	/**
	 * 需要返回参数
	 */
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	@Async
//	@Override
//	public Future<Integer> saveObject(SysLog entity) {
//		System.out.println("SysLogServiceImpl.save:"+
//				Thread.currentThread().getName());
//		int rows=sysLogDao.insertObject(entity);
//		//try{Thread.sleep(5000);}catch(Exception e) {}
//		return new AsyncResult<Integer>(rows);
//	}

}
