package com.cy.spring.advisor;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;
import com.cy.pj.common.annotation.RequiredLog;
@Component
public class LogAdvisor extends StaticMethodMatcherPointcutAdvisor{
	private static final long serialVersionUID = 138203366907921856L;
	public LogAdvisor() {
		//在特定切入点上要执行的通知
		setAdvice(new LogAdvice());
	}
	//Pointcut
    //方法返回值为true时,则可以为目标方法对象创建代理对象
	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		try {
			Method targetMethod = targetClass.getMethod(method.getName(), method.getParameterTypes());
			//判断RequiredLog.class这个注解是否在targetMethod上，是就返回true，然后会被LogAdvice拦截
			return targetMethod.isAnnotationPresent(RequiredLog.class);
		} catch (Exception e) {
			return false;
		}
	}

}
