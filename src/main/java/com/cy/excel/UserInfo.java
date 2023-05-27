package com.cy.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import lombok.Data;

@SuppressWarnings("deprecation")
@Data
public class UserInfo extends BaseRowModel {
	@ExcelProperty(index = 0)
	private Integer id;
	@ExcelProperty(index = 1)
	private String name;
	@ExcelProperty(index = 2)
	private String url;
	@ExcelProperty(index = 3)
	private String parentName;
	@ExcelProperty(index = 4)
	private Integer type;
	@ExcelProperty(index = 5)
	private Integer sort;
	@ExcelProperty(index = 6)
	private String note;
	@ExcelProperty(index = 7)
	private Integer parentId;
	@ExcelProperty(index = 8)
	private String permission;

}
