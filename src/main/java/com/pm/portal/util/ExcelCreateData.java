package com.pm.portal.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExcelCreateData {

	public static void main(String[] args) throws Exception {
		// 表头
		List<String> headerList = new ArrayList<String>();
		headerList.add("姓名");
		headerList.add("电话");
		headerList.add("身份证");
		headerList.add("序号");
		headerList.add("数据");
		
		// 数据
		List<List<Object>> objects = new ArrayList<List<Object>>();
        for (int i = 0; i < 1000; i++) {
            List<Object> dataA = new ArrayList<Object>();
            dataA.add("姓名"+(i+1));
            dataA.add("电话"+(i+1));
            dataA.add("身份证"+(i+1));
            dataA.add("序号"+(i+1));
            dataA.add(i+1);
            objects.add(dataA);
        }
        
		// 表头2
		List<String> headerList2 = new ArrayList<String>();
		headerList2.add("姓名");
		headerList2.add("电话");
		headerList2.add("身份证");
		headerList2.add("序号");
		headerList2.add("数据");
		
		// 数据2
		List<List<Object>> objects2 = new ArrayList<List<Object>>();
        for (int i = 0; i < 1000; i++) {
            List<Object> dataA = new ArrayList<Object>();
            dataA.add("姓名"+(i+1));
            dataA.add("电话"+(i+1));
            dataA.add("身份证"+(i+1));
            dataA.add("序号"+(i+1));
            dataA.add(i+1);
            objects2.add(dataA);
        }

		ExcelCreateUtil excel = new ExcelCreateUtil();
		System.out.println(1);
		System.out.println(excel.excelCreateUtil(headerList, objects,headerList2,objects2));
	}
}


