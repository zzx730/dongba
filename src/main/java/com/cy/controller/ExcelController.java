package com.cy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.excel.ExcelUtil;
import com.cy.excel.UserInfo;
import com.cy.excel.UserInfoDataListener;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

@Controller
public class ExcelController {
	@Autowired
	private SysMenuService sysMenuService;
	@RequestMapping(value = "/file/testExcelDownload")
    public void testExcelDownload(HttpServletRequest request,HttpServletResponse response){
        //以下信息从数据库中查出
		Logger log = LoggerFactory.getLogger(UserInfoDataListener.class);
        List<UserInfo> excelInfos = new ArrayList<>();
        List<SysMenu> list =sysMenuService.findObjects();
        for (SysMenu sysMenu : list) {
        	UserInfo infoi = new UserInfo();
        	infoi.setId(sysMenu.getId());
        	infoi.setName(sysMenu.getName());
        	infoi.setUrl(sysMenu.getUrl());
        	infoi.setType(sysMenu.getType());
        	infoi.setSort(sysMenu.getSort());
        	infoi.setNote(sysMenu.getNote());
        	infoi.setParentId(sysMenu.getParentId());
        	infoi.setPermission(sysMenu.getPermission());
        	infoi.setParentName(sysMenu.getParentName());
        	excelInfos.add(infoi);
		}
        try {
            String fileName = "桂园C307";
            String sheetName = "sheet1";
            ExcelUtil.writeExcel(response, excelInfos, fileName, sheetName, UserInfo.class);
        } catch(Exception e){
            log.error("模板下载失败",e);
        }

    }
}
