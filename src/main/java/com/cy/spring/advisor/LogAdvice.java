package com.cy.spring.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
@Component
public class LogAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("start:"+System.currentTimeMillis());
		Object result = invocation.proceed();
		System.out.println("end:"+System.currentTimeMillis());
		return result;
	}

}
