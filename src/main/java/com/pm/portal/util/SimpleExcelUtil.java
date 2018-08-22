package com.pm.portal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

public class SimpleExcelUtil {
    public static <E> HSSFWorkbook expExcel(String sheetName, List<String> headList, List<String> colNames, String data) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = null;
        for (int i = 0, size = headList.size(); i < size; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headList.get(i));
        }
        JSONArray bodyList = JSONArray.parseArray(data);
        for (int i = 0, size = bodyList.size(); i < size; i++) {
            row = sheet.createRow(i + 1);
            JSONObject rowInfo = bodyList.getJSONObject(i);
            for (int j = 0, row_size = colNames.size(); j < row_size; j++) {
                cell = row.createCell(j);
                cell.setCellValue(rowInfo.getString(colNames.get(j)));
            }
        }
        return workbook;
    }
}
