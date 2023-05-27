package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootTest
public class SysMenuDaoTests {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysMenuService sysMenuService;
	
	@Test
	void testFindObjects() 
	{
		List<SysMenu> list = sysMenuDao.findObjects();
		log.info("list.size {}",list.size());
//		for (Map<String, Object> map : list) {
//			System.out.println(map);
//		}
		//list.forEach(item->System.out.println(item));//JDK8 lamda表达式
		list.forEach(System.out::println);//JDK8中方法引用
		
	}
	@Test
	void testDeleteObject() 
	{
		int rows = sysMenuService.deleteObject(115);
		log.info("rows {}",rows);
	}
}
