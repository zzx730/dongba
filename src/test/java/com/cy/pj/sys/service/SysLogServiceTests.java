package com.cy.pj.sys.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.entity.SysLog;

@SpringBootTest
public class SysLogServiceTests {
	@Autowired
	private SysLogService sysLogService;
	@Test
	void testFindPageObcjts() 
	{
		PageObject<SysLog> list = sysLogService.findPageObjects("admin", 1);
		System.out.println(list);
	}
}
