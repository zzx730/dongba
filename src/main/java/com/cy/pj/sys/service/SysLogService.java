package com.cy.pj.sys.service;


import java.util.List;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogService {
	/**
	 * 基于条件进行分页查询
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysLog> findPageObjects(String username,Integer pageCurrent);
	int getRowCount(String username);
	List<SysLog> findPageObjects(String username,Integer startIndex,Integer pageSize);
	int deleteObjects(Integer... ids);
	void saveObject(SysLog entity);
}
