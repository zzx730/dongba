package com.cy.pj.common.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基于此对象封装业务执行结果
 * 
 * 在java语言，可以简单讲内存中的对象分为俩大类：
 * 1)存储数据的对象(设计的关键在属性上)-典型的POJO(VO,BO,DO)
 * 2)执行业务的对象(设计的关键在方法上)-典型的controller,service,dao
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageObject<T> implements Serializable{//POJO中的BO
	/**
	 * 
	 */
	private static final long serialVersionUID = -5351588666553722704L;
	/**当前页的页码值(客户端传递)*/
	private Integer pageCurrent;
	/**页面大小*/
	private Integer pageSize;
	/**总记录数(从数据库查询获得)*/
	private Integer rowCount;
	/**总页数(基于rowCount和页面大小计算出来的)*/
	private Integer pageCount;
	/**当前页记录,List集合中的T由PageObject类上定义的泛型决定的*/
	private List<T> records;
	public PageObject(Integer pageCurrent, Integer pageSize, Integer rowCount, List<T> records) {
		super();
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		this.records = records;
		//            this.pageCount=rowCount/pageSize;
		//            if(rowCount%pageSize!=0) {
		//                    pageCount++;
		//            }
		this.pageCount=(rowCount-1)/pageSize+1;
	}
	
}
