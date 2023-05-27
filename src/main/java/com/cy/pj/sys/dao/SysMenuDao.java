package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cy.pj.common.pojo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.vo.SysUserMenuVo;
@Mapper
public interface SysMenuDao {
	List<SysMenu> findObjects();
	//删除菜单自身信息
	@Delete("delete from sys_menus where id=#{id}")
	int deleteObject(Integer id);
	//基于菜单id统计菜单对应的子菜单
	@Select("select count(*) from sys_menus where parentId=#{id}")
	int getChildCount(Integer id);
	List<String> findPermissions(Integer[] menuIds);
	@Select("select id,name,parentId from sys_menus")
	List<Node> findZtreeMenuNodes();
	int insertObject(SysMenu entity);
	@Update("update sys_menus set name=#{name},type=#{type},sort=#{sort},url=#{url},parentId=#{parentId},permission=#{permission},modifiedUser=#{modifiedUser},modifiedTime=now() where id=#{id}")
	int updateObject(SysMenu entity);
	List<SysUserMenuVo> findMenusByIds(List<Integer> menuIds);
}
