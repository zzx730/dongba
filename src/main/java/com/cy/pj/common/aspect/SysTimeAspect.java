package com.cy.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SysTimeAspect {
	@Pointcut("bean(sysUserServiceImpl)")
	public void doTime(){}
	
	@Before("doTime()")
	public void doBefore(){
		System.out.println("time doBefore()");
	}
	
	@After("doTime()")
	public void doAfter(){
		System.out.println("time doAfter()");
	}
	
	@AfterReturning("doTime()")
	public void doAfterReturning() 
	{
		System.out.println("time doAfterReturning()");
	}
	
	@AfterThrowing("doTime()")
	public void doAfterThrowing() 
	{
		System.out.println("time doAfterThrowing()");
	}
	
	@Around("doTime()")
	public Object doAround(ProceedingJoinPoint pj) throws Throwable
	{
		System.out.println("doAround.before");
		Object object;
		try {
			object = pj.proceed();
			System.out.println("doAround.after");
		} catch (Throwable e) {
			System.out.println(e.getMessage());
			throw e;
		}
		return object;
	}
	
	
}
