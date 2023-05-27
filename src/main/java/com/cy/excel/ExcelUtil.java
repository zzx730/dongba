package com.cy.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

public class ExcelUtil {

    public static OutputStream getOutputStream(String fileName, HttpServletResponse response)
            throws Exception{
        try{
            fileName = URLEncoder.encode(fileName,"utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //此处指定了文件类型为xls，如果是xlsx的，请自行替换修改
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e){
            throw new Exception("导出文件失败！");
        }
    }

    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list, String fileName,
                                  String sheetName, Class clazz) throws Exception {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLS);
        Sheet sheet = new Sheet(1, 0, clazz);
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        writer.finish();
    }

}
