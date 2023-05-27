package com.cy.pj.sys.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@SpringBootTest
public class SysLogDaoTests {
	@Autowired
	private SysLogDao sysLogDao;
	@Autowired
	private SysLogService sysLogService;
	@Test
	public void testGetRowCount() 
	{
		int rows = sysLogDao.getRowCount("admin");
		System.out.println(rows);
		//list.forEach(item->System.out.println(item);)
	}
	@Test
	public void testFindPageObject() 
	{
		List<SysLog> list = sysLogDao.findPageObjects("admin", 0, 5);
		for (SysLog sysLog : list) {
			System.out.println(list);
		}
	}
	@Test
	public void testFindPageObjects()
	{
		PageObject<SysLog> pageObject =sysLogService.findPageObjects("admin", 1);
		System.out.println(pageObject);
	}
}
