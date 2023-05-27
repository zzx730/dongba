package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.cache.SimpleCache;

@Aspect
@Component
public class SysCacheAspect {
	/**
	 * @annotation(..)为注解方法的切入点表达式，
	 * 这里表示由@RequiredCache注解描述的方法为切入点方法
	 * 
	 */
	@Autowired
	private SimpleCache simpleCache;
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiredCache)")
	public void doCache() {}
	@Around("doCache()")
	public Object around(ProceedingJoinPoint pj)throws Throwable
	{
		System.out.println("Get data from cache");
		Object obj = simpleCache.getObject("deptCache");
		if(obj!=null)return obj;
		Object object = pj.proceed();
		System.out.println("Put data to cache");
		simpleCache.putObject("deptCache", object);
		return object;
		
	}
	@Pointcut("@annotation(com.cy.pj.common.annotation.ClearCache)")
	public void doClear() {}
	@AfterReturning("doClear()")
	public void doClearCache() 
	{
		System.out.println("Clear data to cache");
		simpleCache.clearObject();
	}
}
