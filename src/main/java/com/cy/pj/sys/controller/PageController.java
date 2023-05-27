package com.cy.pj.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.pj.common.util.ShiroUtils;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysLogService;
import com.cy.pj.sys.service.SysMenuService;
import com.cy.pj.sys.vo.SysUserMenuVo;

@Controller
@RequestMapping("/")
public class PageController {
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysMenuService sysMenuService;

	@RequestMapping("doIndexUI")
	public String doIndexUI(Model model) 
	{
		SysUser user = ShiroUtils.getUser();
		model.addAttribute("user", user);
		List<SysUserMenuVo> userMenus=sysMenuService.findUserMenusByUserId(user.getId());
		model.addAttribute("userMenus",userMenus);
		return "starter";
	}

	@RequestMapping("doPageUI")
	public String doPageUI()
	{
		return "common/page";
	}
	@RequestMapping("doLoginUI")
	public String doLoginUI()
	{
		return "login";
	}
//	@RequestMapping("log/log_list")
//	public String doLogUI() 
//	{
//		return "sys/log_list";
//	}
//	
//	@RequestMapping("menu/menu_list")
//	public String doMenuUI()
//	{
//		return "sys/menu_list";
//	}
	//rest 风格(一种架构风格)的 url,其语法结构{变量名}/{变量}
	//@PathVariable注解用于修饰方法参数,可以从rest风格的url中取和参数名对应的值
	@RequestMapping("{module}/{moduleUI}")
	public String doModuleUI(@PathVariable String moduleUI) {
		return "sys/"+moduleUI;
	}

}
