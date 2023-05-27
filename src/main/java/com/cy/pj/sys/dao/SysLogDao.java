package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.entity.SysLog;

@Mapper
public interface SysLogDao {
	/**
	 * 基于条件查询用户行为日志记录总数
	 * @param username 查询条件
	 * @return 查询到的记录总数
	 */
	int getRowCount(String username);
	/**
	 * 基于条件查询当前页记录
	 * @param username 查询条件
	 * @param startIndex 当前页的起始位置
	 * @param pageSize 当前页的页面大小
	 * @return 当前页的日志记录信息
	 * 数据库中每条日志信息封装到一个SysLog对象中
	 */
	List<SysLog> findPageObjects(String username,Integer startIndex,Integer pageSize);
	int deleteObjects(Integer... ids);
	int insertObject(@Param("entity")SysLog entity);

}
