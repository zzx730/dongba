package com.cy.pj.common.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@Data
public class SysRoleMenuVo implements Serializable {
	private static final long serialVersionUID = 2243454299891921701L;
	 private Integer id;
     private String name;
     private String note;
     /**角色对应的菜单id*/
     private List<Integer> menuIds;
}
