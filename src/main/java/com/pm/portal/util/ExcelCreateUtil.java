package com.pm.portal.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelCreateUtil {
	public HSSFWorkbook excelCreateUtil(List headerList, List<List<Object>> objects) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		// 创建字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 12);
		// 设置excel每列宽度
		for (int i = 0; i < headerList.size(); i++) {
			sheet.setColumnWidth(i, 4000);
		}
		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		short df = wb.createDataFormat().getFormat("yyyy-mm-dd");
		style.setDataFormat(df);
		// 设置边框
		// 设置字体
		style.setFont(font);
		// 自动换行
		// style.setWrapText(true);

		int rownum = 0;
		// 表头
		HSSFRow row = sheet.createRow(rownum++);
		for (int i = 0; i < headerList.size(); i++) {
			// 创建一个Excel的单元格
			HSSFCell cell = row.createCell(i);
			// 设置单元格内容格式
			cell.setCellStyle(style);
			// 设置单元格值
			cell.setCellValue(headerList.get(i).toString());
		}

		// 内容数据
		// 遍历集合数据,产生数据行,前两行为标题行与表头行
		for (List<Object> dataRow : objects) {
			row = sheet.createRow(rownum);
			rownum++;
			for (int j = 0; j < dataRow.size(); j++) {
				HSSFCell contentCell = row.createCell(j);
				Object dataObject = dataRow.get(j);
				if (dataObject != null) {
					// System.out.println(dataObject);
					if (dataObject instanceof Date) {
						// System.out.println("data go in");
						contentCell.setCellValue((Date) dataObject);
					} else {
						contentCell.setCellValue(dataObject.toString());

					}
					contentCell.setCellStyle(style);
				} else {
					// 设置单元格内容为字符型
					contentCell.setCellValue("");
				}
				contentCell.setCellStyle(style);

			}
		}

		return wb;

		// 自动调整每一列宽度
		// sheet.autoSizeColumn((short)0);

		// java下载
		// try {
		// FileOutputStream os = new FileOutputStream("D:\\workbook.xls");
		// wb.write(os);
		// os.close();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// javaWeb页面导出

	}

	private String parseExcel(Cell cell) {
		String result = new String();
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				result = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				result = sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:// String类型
			result = cell.getRichStringCellValue().toString();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
		default:
			result = "";
			break;
		}
		return result;
	}

	public HSSFWorkbook excelCreateUtil(List<String> headerList, List<List<Object>> objects,
			List<String> aggsHeaderList, List<List<Object>> aggObjects) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFSheet sheet2 = wb.createSheet("sheet2");
		// 创建字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("Calibri");
		font.setFontHeightInPoints((short) 12);
		// 设置excel每列宽度
		for (int i = 0; i < headerList.size(); i++) {
			sheet.setColumnWidth(i, 4000);
		}
		for (int i = 0; i < aggsHeaderList.size(); i++) {
			sheet2.setColumnWidth(i, 4000);
		}
		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		short df = wb.createDataFormat().getFormat("yyyy-mm-dd");
		style.setDataFormat(df);
		// 设置边框
		// 设置字体
		style.setFont(font);
		// 自动换行
		// style.setWrapText(true);

		int rownum = 0;
		// 表头
		HSSFRow row = sheet.createRow(rownum++);
		for (int i = 0; i < headerList.size(); i++) {
			// 创建一个Excel的单元格
			HSSFCell cell = row.createCell(i);
			// 设置单元格内容格式
			cell.setCellStyle(style);
			// 设置单元格值
			cell.setCellValue(headerList.get(i).toString());
		}

		// 内容数据
		// 遍历集合数据,产生数据行,前两行为标题行与表头行
		for (List<Object> dataRow : objects) {
			row = sheet.createRow(rownum);
			rownum++;
			for (int j = 0; j < dataRow.size(); j++) {
				HSSFCell contentCell = row.createCell(j);
				Object dataObject = dataRow.get(j);
				if (dataObject != null) {
					// System.out.println(dataObject);
					if (dataObject instanceof Date) {
						// System.out.println("data go in");
						contentCell.setCellValue((Date) dataObject);
					} else {
						contentCell.setCellValue(dataObject.toString());

					}
					contentCell.setCellStyle(style);
				} else {
					// 设置单元格内容为字符型
					contentCell.setCellValue("");
				}
				contentCell.setCellStyle(style);

			}
		}
		
		rownum = 0;
		HSSFRow row2 = sheet2.createRow(rownum++);
		for (int i = 0; i < aggsHeaderList.size(); i++) {
			// 创建一个Excel的单元格
			HSSFCell cell = row2.createCell(i);
			// 设置单元格内容格式
			cell.setCellStyle(style);
			// 设置单元格值
			cell.setCellValue(aggsHeaderList.get(i).toString());
		}

		// 内容数据
		// 遍历集合数据,产生数据行,前两行为标题行与表头行
		for (List<Object> dataRow : aggObjects) {
			row = sheet2.createRow(rownum);
			rownum++;
			for (int j = 0; j < dataRow.size(); j++) {
				HSSFCell contentCell = row.createCell(j);
				Object dataObject = dataRow.get(j);
				if (dataObject != null) {
					// System.out.println(dataObject);
					if (dataObject instanceof Date) {
						// System.out.println("data go in");
						contentCell.setCellValue((Date) dataObject);
					} else {
						contentCell.setCellValue(dataObject.toString());

					}
					contentCell.setCellStyle(style);
				} else {
					// 设置单元格内容为字符型
					contentCell.setCellValue("");
				}
				contentCell.setCellStyle(style);

			}
		}

		return wb;
	}
}
