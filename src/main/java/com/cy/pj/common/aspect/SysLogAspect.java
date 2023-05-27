package com.cy.pj.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
//@Order(1)
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;
	/**
	 * PointCut注解用于定义切入点，具体方式可以基于特定表达式进行实现。例如
	 * 1)bean为一种切入点表达式类型
	 * 2)sysUserServiceImpl为spring容器中的一个bean的名字
	 * 	这里的含义是当sysUserServiceImpl对象中的任意方法执行时，都由本切面
	 * 	对象的通知方法做功能增强
	 */
	//@Pointcut("bean(sysUserServiceImpl)")
	@Pointcut("@annotation(com.cy.pj.common.annotation.RequiredLog)")
	public void doLogPointCut() {}//此方法中不需要写任何代码
	/**
	 * 环绕通知：此环绕通知使用的切入点为bean(sysUserServiceImpl)
	 * 环绕通知特点：
	 * 	1)编写：
	 * 		a)方法的返回值为Object
	 * 		b)方法参数为ProceedingJoinPoint类型
	 * 		c)方法抛出异常为throwable
	 * 	2)应用：
	 * 		a)目标方法执行之前或之后可以进行功能拓展
	 * 		b)相对于其他通知优先级最高
	 * 	
	 * @param jp 为一个连接对象(封装了正在要执行的目标方法信息)
	 * @return 目标方法的执行结果
	 * @throws Throwable
	 */
	@Around("doLogPointCut()")
	public Object around(ProceedingJoinPoint jp)throws Throwable
	{
		try {
			long start= System.currentTimeMillis();
			log.info("method start{}",System.currentTimeMillis());
			Object result = jp.proceed();//调用下一个切面方法或目标方法
			log.info("method end{}",System.currentTimeMillis());
			long time= System.currentTimeMillis()-start;
			System.out.println("SysLogServiceImpl.save:"+Thread.currentThread().getName());
			saveUserLog(jp, time);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	private void saveUserLog(ProceedingJoinPoint jp,long time) throws Throwable
	{
		//获取目标对象(要执行的那个目标业务对象)类型
		Class<?> targetCls = jp.getTarget().getClass();
		//获取方法签名对象(此对象中封装了要执行的目标方法信息)
		MethodSignature ms = (MethodSignature)jp.getSignature();
		//获取目标方法对象，基于此对象获取方法上的RequiredLog注解，进而取到操作名
		Method targetMethod = targetCls.getMethod(ms.getName(), ms.getParameterTypes());
		RequiredLog required=targetMethod.getAnnotation(RequiredLog.class);
		//获取操作名
		//String operation="operation";
//		 if(required!=null) {//注解方式的切入点无须做此判断
//			   operation=required.value();
//		   }
		String operation=required.value();
		//获取目标方法类全名
		String targetMethodName=targetCls.getName()+"."+ms.getName();
		//2.构建用户行为日志对象封装用户行为信息
		SysLog log = new SysLog();
		log.setIp(IPUtils.getIpAddr());
		log.setUsername("liuqing");//将来登录的用户
		log.setOperation(operation);
		log.setMethod(targetMethodName);
		//log.setParams(Arrays.toString(jp.getArgs()));//将参数对象转换成普通串
		log.setParams(new ObjectMapper().writeValueAsString(jp.getArgs()));//将参数对象尽量转换成json格式字符串
		log.setTime(time);
		log.setCreatedTime(new Date());
		//用户行为日志写入到数据库
		sysLogService.saveObject(log);
		//new thread()//高并发环境下很快就会内存溢出
		
	}
}
