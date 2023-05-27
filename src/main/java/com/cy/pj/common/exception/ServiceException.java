package com.cy.pj.common.exception;

/**
 * 自定义非检查异常
 * 目的：为了对业务中的信息进行更好的反馈和定位
 *
 */
public class ServiceException extends RuntimeException{
	private static final long serialVersionUID = 7793296502722655579L;
	public ServiceException() 
	{
		super();
	}
	public ServiceException(String message) 
	{
		super(message);
	}
	public ServiceException(Throwable cause) 
	{
		super(cause);
	}
}
