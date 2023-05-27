package com.cy.spring.aop;

import org.springframework.stereotype.Service;

import com.cy.pj.common.annotation.RequiredLog;
@Service
public class DeafultSearchService implements SearchService{
	@RequiredLog
	@Override
	public Object search(String key) {
		System.out.println("search by "+key);
		return null;
	}

}
