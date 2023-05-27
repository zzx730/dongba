package com.cy;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cy.spring.aop.SearchService;

@SpringBootTest
class ApplicationTests {
	@Autowired
	private SearchService searchService;
	@Test
	void contextLoads() {
		searchService.search("good");
	}
	@Test
	void MD5() 
	{
		SimpleHash sh = new SimpleHash("MD5","123456","7edec206-127a-4367-9105-a40c51e4bf30",1);
		System.out.println(sh.toHex());
	}

}
