package com.home.poi;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author: xu.dm
 * @Date: 2020/12/15 11:20
 * @Version: 1.0
 * @Description: TODO
 **/
public class PMExcel {
    public static void main(String[] args) throws Exception {

        createAppointment();
    }

    public static void createAppointment() throws Exception {
        File file = new File("D:\\temp\\pm.xlsx");
        File outFile = new File("D:\\temp\\pm_out.xlsx");
        FileOutputStream out = new FileOutputStream(outFile);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        // 表格编号
        Cell cell = sheet.getRow(2).getCell(0);
        cell.setCellValue("表格编号：F80402-202016-999");
        // 项目编号
        cell = sheet.getRow(3).getCell(0);
        cell.setCellValue("项目编号：DJRJ999");
        // 项目名称
        cell = sheet.getRow(4).getCell(0);
        cell.setCellValue("项目名称：天庭与西方极乐合作开发新一代西游世界2020孙悟空打不过白骨精的项目新合同");
        // 项目经理
        cell = sheet.getRow(6).getCell(1);
        cell.setCellValue("项目经理：张三");
        // 联系方式
        cell = sheet.getRow(7).getCell(1);
        cell.setCellValue("联系方式：12345678901");

        // 开发经理
        cell = sheet.getRow(9).getCell(1);
        cell.setCellValue("开发经理：李四");
        // 联系方式
        cell = sheet.getRow(10).getCell(1);
        cell.setCellValue("联系方式：92345678901");

        // 实施经理
        cell = sheet.getRow(12).getCell(1);
        cell.setCellValue("实施经理：王五");
        // 联系方式
        cell = sheet.getRow(13).getCell(1);
        cell.setCellValue("联系方式：82345678901");

        // 客户经理
        cell = sheet.getRow(15).getCell(1);
        cell.setCellValue("客户经理：赵柳");
        // 联系方式
        cell = sheet.getRow(16).getCell(1);
        cell.setCellValue("联系方式：72345678901");

        // 客户经理
        cell = sheet.getRow(18).getCell(1);
        cell.setCellValue("客户经理：郭七");
        // 联系方式
        cell = sheet.getRow(19).getCell(1);
        cell.setCellValue("联系方式：62345678901");

        // 签发人
        cell = sheet.getRow(23).getCell(2);
        cell.setCellValue("签发人：孙悟空");
        // 签发日期
        cell = sheet.getRow(24).getCell(2);
        cell.setCellValue("日期：2020-12-12");

        // 签发部门
        cell = sheet.getRow(26).getCell(2);
        cell.setCellValue("（天庭项目部）");

        workbook.write(out);
        workbook.close();
        IOUtils.close(out);
    }
}
