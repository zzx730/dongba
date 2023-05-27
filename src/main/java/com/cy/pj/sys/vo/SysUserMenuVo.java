package com.cy.pj.sys.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class SysUserMenuVo implements Serializable{
	private static final long serialVersionUID = 451429010085820070L;
	private Integer id;
	private String name;
	private String url;
	private List<SysUserMenuVo> childs;
}
