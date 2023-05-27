package com.cy.pj.common.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 基于此对象封装服务端要响应到客户端的数据，这个数据包含：
 * 1)状态码(表示这个响应结果是正确的还是错误)
 * 2)状态信息(状态码对应的状态信息)
 * 3)正常的响应数据(例如一个查询结果)
 * 
 * POJO:(VO-view Object封装了表现层要呈现的数据)
 */
@Data
@NoArgsConstructor
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -856924038217431339L;//SysResult/Result/R
	/**状态码*/
	private int state=1;//1表示SUCCESS,0表示ERROR
	/**状态信息*/
	private String message="ok";
	/**正确的响应数据*/
	private Object data;
	public JsonResult(String message){
		this.message=message;
	}
	/**一般查询时调用，封装查询结果*/
	public JsonResult(Object data) {
		this.data=data;
	}
	/**出现异常时时调用*/
	public JsonResult(Throwable t){//Throwable为所有异常的父类
		this.state=0;
		this.message=t.getMessage();
	}
}
