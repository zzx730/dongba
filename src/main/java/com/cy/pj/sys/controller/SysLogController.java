package com.cy.pj.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.pojo.JsonResult;
import com.cy.pj.common.pojo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
//@RestController===@Controller+@ResponseBody
@Controller
@RequestMapping("log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent){
		PageObject<SysLog> pageObject = sysLogService.findPageObjects(username,pageCurrent);
		//控制层对业务数据再次封装
		return new JsonResult(pageObject);//此位置封装业务的正确数据
	}
	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult doDeleteObjects(Integer... ids){
		sysLogService.deleteObjects(ids);
		return new JsonResult("delete ok!");
	}
	@RequestMapping("/log/dogetRowResult")
	@ResponseBody
	public int getRowResult(String username){
		return sysLogService.getRowCount("admin");
	}
	@RequestMapping("/log/dofindPageObjects")
	@ResponseBody
	public List<SysLog> findPageObjects(){
		System.out.println(111);
		return sysLogService.findPageObjects("admin", 0, 3);
	}
}
