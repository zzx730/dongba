package com.cy.pj.common.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Aspect
@Component
public class SysExceptionAspect {
	@AfterThrowing(pointcut="bean(*ServiceImpl)",throwing = "e")
	public void doHandleException(JoinPoint jp,Throwable e) {
		//通过连接点对象获取正在执行的目标对象类型的名称
		String targetClassName = jp.getTarget().getClass().getName();
		MethodSignature ms=(MethodSignature)jp.getSignature();
		log.error("{} invoke exception msg is {}",targetClassName+"."+ms.getName(),e.getMessage());
	}

}
