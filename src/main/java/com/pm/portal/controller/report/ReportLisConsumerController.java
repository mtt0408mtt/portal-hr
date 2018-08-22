package com.pm.portal.controller.report;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pm.portal.domain.report.ItemAggrExcelReport;
import com.pm.portal.domain.report.ItemExcelReport;
import com.pm.portal.domain.report.TmmxAggrExcelReport;
import com.pm.portal.domain.report.TmmxExcelReport;
import com.pm.portal.domain.report.TmxxAggrExcelReport;
import com.pm.portal.domain.report.TmxxExcelReport;
import com.pm.portal.redis.RedisService;
import com.pm.portal.result.CodeMsg;
import com.pm.portal.result.Result;
import com.pm.portal.service.report.ReportLisClient;
import com.pm.portal.util.ExcelCreateUtil;

@RestController
@RequestMapping(value = "/report_lis_c")
public class ReportLisConsumerController {

	@Autowired
	private ReportLisClient reportLisClient;

	@RequestMapping(value = "/tmxx_aggr_report", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object tmxxAggrReport(HttpServletResponse response, HttpServletRequest request) {
		int indexGroup = 0;
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			if (null != request.getParameter("startDate") || null != request.getParameter("endDate")) {
				System.out.println("startDate===" + String.valueOf(request.getParameter("startDate")));
				System.out.println("endDate===" + String.valueOf(request.getParameter("endDate")));
				String startDate = String.valueOf(request.getParameter("startDate"));
				String endDate = String.valueOf(request.getParameter("endDate"));
				if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
					vars.put("startDate", startDate);
					vars.put("endDate", endDate);
				}
			} else {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_WHERE_PARAMS_NULL);
			}

			if (null != request.getParameter("tmzt")) {

				String tmzt = String.valueOf(request.getParameter("tmzt"));

				vars.put("tmzt", Arrays.asList(tmzt.split(",")));
			} else {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_WHERE_STATUS_PARAMS_NULL);
			}

			if (null != request.getParameter("jgid")) {
				Boolean jgid = Boolean.valueOf(request.getParameter("jgid"));
				if (!StringUtils.isEmpty(jgid) && jgid) {
					vars.put("jgid", jgid);
					indexGroup++;
				}

			}

			if (null != request.getParameter("sjdw")) {
				Boolean sjdw = Boolean.valueOf(request.getParameter("sjdw"));
				if (!StringUtils.isEmpty(sjdw) && sjdw) {
					vars.put("sjdw", sjdw);
					indexGroup++;
				}
			}

			if (null != request.getParameter("zhmcStr")) {
				Boolean zhmcStr = Boolean.valueOf(request.getParameter("zhmcStr"));
				if (!StringUtils.isEmpty(zhmcStr) && zhmcStr) {
					vars.put("zhmcStr", zhmcStr);
					indexGroup++;
				}

			}

			if (null != request.getParameter("sjdw")) {
				Boolean sjdw = Boolean.valueOf(request.getParameter("sjdw"));
				if (!StringUtils.isEmpty(sjdw) && sjdw) {
					vars.put("sjdw", sjdw);
					indexGroup++;
				}
			}

			if (null != request.getParameter("yqbh")) {
				Boolean yqbh = Boolean.valueOf(request.getParameter("yqbh"));
				if (!StringUtils.isEmpty(yqbh) && yqbh) {
					vars.put("yqbh", yqbh);
					indexGroup++;
				}
			}

			if (null != request.getParameter("jyr")) {
				Boolean jyr = Boolean.valueOf(request.getParameter("jyr"));

				if (!StringUtils.isEmpty(jyr) && jyr) {
					vars.put("jyr", jyr);
					indexGroup++;
				}
			}

			if (null != request.getParameter("jyks")) {
				Boolean jyks = Boolean.valueOf(request.getParameter("jyks"));
				if (!StringUtils.isEmpty(jyks) && jyks) {
					vars.put("jyks", jyks);
					indexGroup++;
				}
			}

			if (indexGroup < 1) {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_CATEGORY_PARAMS_NULL);
			}
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return reportLisClient.tmxxAggrReport(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}

	@RequestMapping(value = "/to_export")
	public void export(HttpServletRequest request, HttpServletResponse response,
			Model model /* , PMAIAdmin admin */) {
		OutputStream ouputStream = null;
		int indexGroup = 0;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=workbook.xls");
			// if (admin == null) {
			// String beanToString = RedisService.beanToString("session not
			// found!");
			// ouputStream=response.getOutputStream();
			// ouputStream.write(beanToString.getBytes("UTF-8"));
			// ouputStream.flush();
			// } else {

			if (null != request.getParameter("search_name_filter") || null != request.getParameter("params")) {
				String search_name = request.getParameter("search_name_filter");
				String params = request.getParameter("params");
				Map<String, Object> vars = new HashMap<String, Object>();
				System.out.println("search_name," + search_name);
				System.out.println("params," + params);
				if(search_name!=null && !StringUtils.isEmpty(search_name)){
					System.out.println("search_name," + search_name);
					vars.put("search_name", search_name);	
				}
				
				Map mapObj = JSONObject.parseObject(params, Map.class);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				if (null != mapObj.get("startDate") || null != mapObj.get("endDate")) {
					System.out.println("startDate===" + String.valueOf(mapObj.get("startDate")));
					System.out.println("endDate===" + String.valueOf(mapObj.get("endDate")));
					String startDate = String.valueOf(mapObj.get("startDate"));
					String endDate = String.valueOf(mapObj.get("endDate"));
					if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
						vars.put("startDate", startDate);
						vars.put("endDate", endDate);
					}
				} else {
					exportMsg(response, "startDate and endDate is null");
				}

				if (null != mapObj.get("tmzt")) {

					String tmzt = String.valueOf(mapObj.get("tmzt"));

					vars.put("tmzt", Arrays.asList(tmzt.split(",")));
				} else {
					exportMsg(response, "tmzt is null");
				}

				if (null != mapObj.get("jgid")) {
					Boolean jgid = Boolean.valueOf(String.valueOf(mapObj.get("jgid")));
					if (!StringUtils.isEmpty(jgid) && jgid) {
						vars.put("jgid", jgid);
						indexGroup++;
					}

				}

				if (null != mapObj.get("sjdw")) {
					Boolean sjdw = Boolean.valueOf(String.valueOf(mapObj.get("sjdw")));
					if (!StringUtils.isEmpty(sjdw) && sjdw) {
						vars.put("sjdw", sjdw);
						indexGroup++;
					}
				}

				if (null != mapObj.get("zhmcStr")) {
					Boolean zhmcStr = Boolean.valueOf(String.valueOf(mapObj.get("zhmcStr")));
					if (!StringUtils.isEmpty(zhmcStr) && zhmcStr) {
						vars.put("zhmcStr", zhmcStr);
						indexGroup++;
					}

				}

				if (null != mapObj.get("sjdw")) {
					Boolean sjdw = Boolean.valueOf(String.valueOf(mapObj.get("sjdw")));
					if (!StringUtils.isEmpty(sjdw) && sjdw) {
						vars.put("sjdw", sjdw);
						indexGroup++;
					}
				}

				if (null != mapObj.get("yqbh")) {
					Boolean yqbh = Boolean.valueOf(String.valueOf(mapObj.get("yqbh")));
					if (!StringUtils.isEmpty(yqbh) && yqbh) {
						vars.put("yqbh", yqbh);
						indexGroup++;
					}
				}

				if (null != mapObj.get("jyr")) {
					Boolean jyr = Boolean.valueOf(String.valueOf(mapObj.get("jyr")));

					if (!StringUtils.isEmpty(jyr) && jyr) {
						vars.put("jyr", jyr);
						indexGroup++;
					}
				}

				if (null != mapObj.get("jyks")) {
					Boolean jyks = Boolean.valueOf(String.valueOf(mapObj.get("jyks")));
					if (!StringUtils.isEmpty(jyks) && jyks) {
						vars.put("jyks", jyks);
						indexGroup++;
					}
				}

				if (indexGroup < 1) {
					exportMsg(response, "CATEGORY_PARAMS is null");
				}
				for (Map.Entry<String, Object> entry : vars.entrySet()) {
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				}
				String condition = JSON.toJSONString(vars);
				List<TmxxExcelReport> datas=null;
				String result= reportLisClient.tmxxExportExcel(vars);
				datas=JSONObject.parseArray(result, TmxxExcelReport.class); 
				 
				 // 表头
				 List<String> headerList = new ArrayList<String>();
				 headerList.add("机构名称");
				 headerList.add("送检医院");
				 headerList.add("姓名");
				 headerList.add("性别");
				 headerList.add("条码");
				 headerList.add("条码状态");
				 headerList.add("送检日期");
				 headerList.add("检验组合套餐");
				 headerList.add("仪器名称");
				 headerList.add("检验人");
				 headerList.add("检验科室");
				 headerList.add("申请模式");
				 headerList.add("病人类型");
				 headerList.add("机构ID");
				 headerList.add("送检医院ID");
				 headerList.add("查询条件");
				
				 // 数据
				 List<List<Object>> objects = new ArrayList<List<Object>>();
				 for (int i = 0; i < datas.size(); i++) {
				 TmxxExcelReport bean = datas.get(i);
				 List<Object> dataA = new ArrayList<Object>();
				 dataA.add("" + bean.getJgmc());
				 dataA.add("" + bean.getSjdwmc());
				 dataA.add("" + bean.getBrxm());
				 dataA.add("" + bean.getSexmc());
				 dataA.add("" + bean.getTmh());
				 dataA.add("" + bean.getTmztmc());
				 dataA.add("" + bean.getZxsj());
				 dataA.add("" + bean.getZhmcStr());
				 
				 dataA.add("" + bean.getSbmc());
				 dataA.add("" + bean.getJyrmc());
				 dataA.add("" + bean.getKsdmmc());
				 dataA.add("" + bean.getSqmsmc());
				 dataA.add("" + bean.getBrlxmc());
				 dataA.add("" + bean.getJgid());
				 dataA.add("" + bean.getSjdw());
				 dataA.add(condition);
				
				 objects.add(dataA);
				 }

				 //汇总
				 List<TmxxAggrExcelReport> aggs=null;
				 String agg= reportLisClient.tmxxAggrExportExcel(vars);
				 aggs=JSONObject.parseArray(agg, TmxxAggrExcelReport.class); 
				 
				 
				 // 汇总表头
				 List<String> aggsHeaderList = new ArrayList<String>();
				 aggsHeaderList.add("机构名称");
				 aggsHeaderList.add("A送检单位");
				 aggsHeaderList.add("B组合套餐");
				 aggsHeaderList.add("C仪器编号");
				 aggsHeaderList.add("D检验人");
				 aggsHeaderList.add("E检验科室");
				 aggsHeaderList.add("Z汇总");
				 aggsHeaderList.add("查询条件");
				 
				 // 数据
				 List<List<Object>> aggObjects = new ArrayList<List<Object>>();
				 for (int i = 0; i < aggs.size(); i++) {
					 TmxxAggrExcelReport bean = aggs.get(i);
					 List<Object> dataA = new ArrayList<Object>();
					 dataA.add("" + bean.getJgmc());
					 dataA.add("" + bean.getSjdwmc());
					 dataA.add("" + bean.getZhmcStr());
					 dataA.add("" + bean.getSbmc());
					 dataA.add("" + bean.getJyrmc());
					 dataA.add("" + bean.getKsdmmc());
					 dataA.add("" + bean.getEncount());
					 dataA.add(condition);
					 aggObjects.add(dataA);
				 }
				 ExcelCreateUtil excel = new ExcelCreateUtil();
				 HSSFWorkbook wb = excel.excelCreateUtil(headerList, objects,aggsHeaderList,aggObjects);
				 
				 ouputStream = response.getOutputStream();
				 wb.write(ouputStream);
				 ouputStream.flush();

			} else {
				exportMsg(response, "params map is null");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void exportMsg(HttpServletResponse response, String msg) throws IOException, UnsupportedEncodingException {
		OutputStream ouputStream;
		String beanToString = RedisService.beanToString(msg);
		ouputStream = response.getOutputStream();
		ouputStream.write(beanToString.getBytes("UTF-8"));
		ouputStream.flush();
	}
	
	
	
	@RequestMapping(value = "/tmxx_aggr_report_mx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object tmxxAggrReportMx(HttpServletResponse response, HttpServletRequest request) {
		int indexGroup = 0;
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			if (null != request.getParameter("startDate") || null != request.getParameter("endDate")) {
				System.out.println("startDate===" + String.valueOf(request.getParameter("startDate")));
				System.out.println("endDate===" + String.valueOf(request.getParameter("endDate")));
				String startDate = String.valueOf(request.getParameter("startDate"));
				String endDate = String.valueOf(request.getParameter("endDate"));
				if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
					vars.put("startDate", startDate);
					vars.put("endDate", endDate);
				}
			} else {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_WHERE_PARAMS_NULL);
			}

			if (null != request.getParameter("tmzt")) {

				String tmzt = String.valueOf(request.getParameter("tmzt"));

				vars.put("tmzt", Arrays.asList(tmzt.split(",")));
			} else {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_WHERE_STATUS_PARAMS_NULL);
			}

			if (null != request.getParameter("jgid")) {
				Boolean jgid = Boolean.valueOf(request.getParameter("jgid"));
				if (!StringUtils.isEmpty(jgid) && jgid) {
					vars.put("jgid", jgid);
					indexGroup++;
				}

			}

			if (null != request.getParameter("sjdw")) {
				Boolean sjdw = Boolean.valueOf(request.getParameter("sjdw"));
				if (!StringUtils.isEmpty(sjdw) && sjdw) {
					vars.put("sjdw", sjdw);
					indexGroup++;
				}
			}

			if (null != request.getParameter("zhmc")) {
				Boolean zhmc = Boolean.valueOf(request.getParameter("zhmc"));
				if (!StringUtils.isEmpty(zhmc) && zhmc) {
					vars.put("zhmc", zhmc);
					indexGroup++;
				}

			}
			
			if (null != request.getParameter("lbmc")) {
				Boolean lbmc = Boolean.valueOf(request.getParameter("lbmc"));
				if (!StringUtils.isEmpty(lbmc) && lbmc) {
					vars.put("lbmc", lbmc);
					indexGroup++;
				}

			}

			if (null != request.getParameter("sjdw")) {
				Boolean sjdw = Boolean.valueOf(request.getParameter("sjdw"));
				if (!StringUtils.isEmpty(sjdw) && sjdw) {
					vars.put("sjdw", sjdw);
					indexGroup++;
				}
			}

			if (null != request.getParameter("yqbh")) {
				Boolean yqbh = Boolean.valueOf(request.getParameter("yqbh"));
				if (!StringUtils.isEmpty(yqbh) && yqbh) {
					vars.put("yqbh", yqbh);
					indexGroup++;
				}
			}

			if (null != request.getParameter("jyr")) {
				Boolean jyr = Boolean.valueOf(request.getParameter("jyr"));

				if (!StringUtils.isEmpty(jyr) && jyr) {
					vars.put("jyr", jyr);
					indexGroup++;
				}
			}

			if (null != request.getParameter("jyks")) {
				Boolean jyks = Boolean.valueOf(request.getParameter("jyks"));
				if (!StringUtils.isEmpty(jyks) && jyks) {
					vars.put("jyks", jyks);
					indexGroup++;
				}
			}

			if (indexGroup < 1) {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_CATEGORY_PARAMS_NULL);
			}
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return reportLisClient.tmmxAggrReport(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}
	
	@RequestMapping(value = "/to_export_mx")
	public void export_mx(HttpServletRequest request, HttpServletResponse response,
			Model model /* , PMAIAdmin admin */) {
		OutputStream ouputStream = null;
		int indexGroup = 0;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=workbook.xls");
			// if (admin == null) {
			// String beanToString = RedisService.beanToString("session not
			// found!");
			// ouputStream=response.getOutputStream();
			// ouputStream.write(beanToString.getBytes("UTF-8"));
			// ouputStream.flush();
			// } else {

			if (null != request.getParameter("search_name_filter") || null != request.getParameter("params")) {
				String search_name = request.getParameter("search_name_filter");
				String params = request.getParameter("params");
				Map<String, Object> vars = new HashMap<String, Object>();
				System.out.println("search_name," + search_name);
				System.out.println("params," + params);
				if(search_name!=null && !StringUtils.isEmpty(search_name)){
					System.out.println("search_name11," + search_name);
					vars.put("search_name", search_name);	
				}
				
				Map mapObj = JSONObject.parseObject(params, Map.class);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				if (null != mapObj.get("startDate") || null != mapObj.get("endDate")) {
					System.out.println("startDate===" + String.valueOf(mapObj.get("startDate")));
					System.out.println("endDate===" + String.valueOf(mapObj.get("endDate")));
					String startDate = String.valueOf(mapObj.get("startDate"));
					String endDate = String.valueOf(mapObj.get("endDate"));
					if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
						vars.put("startDate", startDate);
						vars.put("endDate", endDate);
					}
				} else {
					exportMsg(response, "startDate and endDate is null");
				}

				if (null != mapObj.get("tmzt")) {

					String tmzt = String.valueOf(mapObj.get("tmzt"));

					vars.put("tmzt", Arrays.asList(tmzt.split(",")));
				} else {
					exportMsg(response, "tmzt is null");
				}

				if (null != mapObj.get("jgid")) {
					Boolean jgid = Boolean.valueOf(String.valueOf(mapObj.get("jgid")));
					if (!StringUtils.isEmpty(jgid) && jgid) {
						vars.put("jgid", jgid);
						indexGroup++;
					}

				}

				if (null != mapObj.get("sjdw")) {
					Boolean sjdw = Boolean.valueOf(String.valueOf(mapObj.get("sjdw")));
					if (!StringUtils.isEmpty(sjdw) && sjdw) {
						vars.put("sjdw", sjdw);
						indexGroup++;
					}
				}

				if (null != mapObj.get("zhmc")) {
					Boolean zhmc = Boolean.valueOf(String.valueOf(mapObj.get("zhmc")));
					if (!StringUtils.isEmpty(zhmc) && zhmc) {
						vars.put("zhmc", zhmc);
						indexGroup++;
					}

				}
				
				if (null != mapObj.get("lbmc")) {
					Boolean lbmc = Boolean.valueOf(String.valueOf(mapObj.get("lbmc")));
					if (!StringUtils.isEmpty(lbmc) && lbmc) {
						vars.put("lbmc", lbmc);
						indexGroup++;
					}

				}

				if (null != mapObj.get("sjdw")) {
					Boolean sjdw = Boolean.valueOf(String.valueOf(mapObj.get("sjdw")));
					if (!StringUtils.isEmpty(sjdw) && sjdw) {
						vars.put("sjdw", sjdw);
						indexGroup++;
					}
				}

				if (null != mapObj.get("yqbh")) {
					Boolean yqbh = Boolean.valueOf(String.valueOf(mapObj.get("yqbh")));
					if (!StringUtils.isEmpty(yqbh) && yqbh) {
						vars.put("yqbh", yqbh);
						indexGroup++;
					}
				}

				if (null != mapObj.get("jyr")) {
					Boolean jyr = Boolean.valueOf(String.valueOf(mapObj.get("jyr")));

					if (!StringUtils.isEmpty(jyr) && jyr) {
						vars.put("jyr", jyr);
						indexGroup++;
					}
				}

				if (null != mapObj.get("jyks")) {
					Boolean jyks = Boolean.valueOf(String.valueOf(mapObj.get("jyks")));
					if (!StringUtils.isEmpty(jyks) && jyks) {
						vars.put("jyks", jyks);
						indexGroup++;
					}
				}

				if (indexGroup < 1) {
					exportMsg(response, "CATEGORY_PARAMS is null");
				}
				for (Map.Entry<String, Object> entry : vars.entrySet()) {
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				}
				String condition = JSON.toJSONString(vars);
				List<TmmxExcelReport> datas=null;
				String result= reportLisClient.tmmxExportExcel(vars);
				datas=JSONObject.parseArray(result, TmmxExcelReport.class); 
				 
				 // 表头
				 List<String> headerList = new ArrayList<String>();
				 headerList.add("机构名称");
				 headerList.add("送检医院");
				 headerList.add("姓名");
				 headerList.add("性别");
				 headerList.add("条码");
				 headerList.add("条码状态");
				 headerList.add("送检日期");
				 headerList.add("检验组合项");
				 headerList.add("仪器名称");
				 headerList.add("检验人");
				 headerList.add("检验科室");
				 headerList.add("申请模式");
				 headerList.add("病人类型");
				 headerList.add("机构ID");
				 headerList.add("送检医院ID");
				 headerList.add("类别名称");
				 headerList.add("查询条件");
				
		

				
				
				
				 // 数据
				 List<List<Object>> objects = new ArrayList<List<Object>>();
				 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				 for (int i = 0; i < datas.size(); i++) {
				 TmmxExcelReport bean = datas.get(i);
				 List<Object> dataA = new ArrayList<Object>();
				 dataA.add("" + bean.getJgmc());
				 dataA.add("" + bean.getSjdwmc());
				 dataA.add("" + bean.getBrxm());
				 dataA.add("" + bean.getSexmc());
				 dataA.add("" + bean.getTmh());
				 dataA.add("" + bean.getTmztmc());
				 dataA.add("" + bean.getZxsj());
				 dataA.add("" + bean.getZhmc());
				 
				 dataA.add("" + bean.getSbmc());
				 dataA.add("" + bean.getJyrmc());
				 dataA.add("" + bean.getKsdmmc());
				 dataA.add("" + bean.getSqmsmc());
				 dataA.add("" + bean.getBrlxmc());
				 dataA.add("" + bean.getJgid());
				 dataA.add("" + bean.getSjdw());
				 dataA.add("" + bean.getLbmc());
				 dataA.add(condition);
				
				 objects.add(dataA);
				 }
				 
				 //汇总
				 List<TmmxAggrExcelReport> aggs=null;
				 String agg= reportLisClient.tmmxAggrExportExcel(vars);
				 aggs=JSONObject.parseArray(agg, TmmxAggrExcelReport.class); 
				 
				 
				 // 汇总表头
				 List<String> aggsHeaderList = new ArrayList<String>();
				 aggsHeaderList.add("机构名称");
				 aggsHeaderList.add("A送检单位");
				 aggsHeaderList.add("B组合项");
				 aggsHeaderList.add("C仪器编号");
				 aggsHeaderList.add("D检验人");
				 aggsHeaderList.add("E检验科室");
				 aggsHeaderList.add("F类别名称");
				 aggsHeaderList.add("Z汇总");
				 aggsHeaderList.add("查询条件");
				 
				 // 数据
				 List<List<Object>> aggObjects = new ArrayList<List<Object>>();
				 for (int i = 0; i < aggs.size(); i++) {
					 TmmxAggrExcelReport bean = aggs.get(i);
					 List<Object> dataA = new ArrayList<Object>();
					 dataA.add("" + bean.getJgmc());
					 dataA.add("" + bean.getSjdwmc());
					 dataA.add("" + bean.getZhmc());
					 dataA.add("" + bean.getSbmc());
					 dataA.add("" + bean.getJyrmc());
					 dataA.add("" + bean.getKsdmmc());
					 dataA.add("" + bean.getLbmc());
					 dataA.add("" + bean.getEncount());
					 dataA.add(condition);
					 aggObjects.add(dataA);
				 }
				 ExcelCreateUtil excel = new ExcelCreateUtil();
				 HSSFWorkbook wb = excel.excelCreateUtil(headerList, objects,aggsHeaderList,aggObjects);
				 
				 
				 ouputStream = response.getOutputStream();
				 wb.write(ouputStream);
				 ouputStream.flush();

			} else {
				exportMsg(response, "params map is null");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	private void setParams(HttpServletRequest request, Map<String, Object> vars,String p_name) {
		String name = request.getParameter(p_name);
		if (!StringUtils.isEmpty(name)) {
			vars.put(p_name, name);
		}
	}
	
	
	@RequestMapping(value = "/item_aggr_report", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object tmxxAggrReportItem(HttpServletResponse response, HttpServletRequest request) {
		int indexGroup = 0;
		try {
			Map<String, Object> vars = new HashMap<String, Object>();

			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			if (null != request.getParameter("startDate") || null != request.getParameter("endDate")) {
				System.out.println("startDate===" + String.valueOf(request.getParameter("startDate")));
				System.out.println("endDate===" + String.valueOf(request.getParameter("endDate")));
				String startDate = String.valueOf(request.getParameter("startDate"));
				String endDate = String.valueOf(request.getParameter("endDate"));
				if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
					vars.put("startDate", startDate);
					vars.put("endDate", endDate);
				}
			} else {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_WHERE_PARAMS_NULL);
			}

			if (null != request.getParameter("tmzt")) {

				String tmzt = String.valueOf(request.getParameter("tmzt"));

				vars.put("tmzt", Arrays.asList(tmzt.split(",")));
			} else {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_WHERE_STATUS_PARAMS_NULL);
			}

			if (null != request.getParameter("jgid")) {
				Boolean jgid = Boolean.valueOf(request.getParameter("jgid"));
				if (!StringUtils.isEmpty(jgid) && jgid) {
					vars.put("jgid", jgid);
					indexGroup++;
				}

			}

			if (null != request.getParameter("sjdw")) {
				Boolean sjdw = Boolean.valueOf(request.getParameter("sjdw"));
				if (!StringUtils.isEmpty(sjdw) && sjdw) {
					vars.put("sjdw", sjdw);
					indexGroup++;
				}
			}

			if (null != request.getParameter("zhmc")) {
				Boolean zhmc = Boolean.valueOf(request.getParameter("zhmc"));
				if (!StringUtils.isEmpty(zhmc) && zhmc) {
					vars.put("zhmc", zhmc);
					indexGroup++;
				}

			}
			if (null != request.getParameter("item")) {
				Boolean item = Boolean.valueOf(request.getParameter("item"));
				if (!StringUtils.isEmpty(item) && item) {
					vars.put("item", item);
					indexGroup++;
				}

			}
			
			if (null != request.getParameter("lbmc")) {
				Boolean lbmc = Boolean.valueOf(request.getParameter("lbmc"));
				if (!StringUtils.isEmpty(lbmc) && lbmc) {
					vars.put("lbmc", lbmc);
					indexGroup++;
				}

			}

			if (null != request.getParameter("sjdw")) {
				Boolean sjdw = Boolean.valueOf(request.getParameter("sjdw"));
				if (!StringUtils.isEmpty(sjdw) && sjdw) {
					vars.put("sjdw", sjdw);
					indexGroup++;
				}
			}

			if (null != request.getParameter("yqbh")) {
				Boolean yqbh = Boolean.valueOf(request.getParameter("yqbh"));
				if (!StringUtils.isEmpty(yqbh) && yqbh) {
					vars.put("yqbh", yqbh);
					indexGroup++;
				}
			}

			if (null != request.getParameter("jyr")) {
				Boolean jyr = Boolean.valueOf(request.getParameter("jyr"));

				if (!StringUtils.isEmpty(jyr) && jyr) {
					vars.put("jyr", jyr);
					indexGroup++;
				}
			}

			if (null != request.getParameter("jyks")) {
				Boolean jyks = Boolean.valueOf(request.getParameter("jyks"));
				if (!StringUtils.isEmpty(jyks) && jyks) {
					vars.put("jyks", jyks);
					indexGroup++;
				}
			}

			if (indexGroup < 1) {
				return Result.error(CodeMsg.REPROT_LIS_QUERY_CATEGORY_PARAMS_NULL);
			}
			for (Map.Entry<String, Object> entry : vars.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			return reportLisClient.itemAggrReport(vars);

		} catch (

		Exception e) {
			e.printStackTrace();
			return Result.error(CodeMsg.REPROT_LIS_QUERY_ERROR);
		}
	}
	
	@RequestMapping(value = "/to_export_item")
	public void export_item(HttpServletRequest request, HttpServletResponse response,
			Model model /* , PMAIAdmin admin */) {
		OutputStream ouputStream = null;
		int indexGroup = 0;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=workbook.xls");
			// if (admin == null) {
			// String beanToString = RedisService.beanToString("session not
			// found!");
			// ouputStream=response.getOutputStream();
			// ouputStream.write(beanToString.getBytes("UTF-8"));
			// ouputStream.flush();
			// } else {

			if (null != request.getParameter("search_name_filter") || null != request.getParameter("params")) {
				String search_name = request.getParameter("search_name_filter");
				String params = request.getParameter("params");
				Map<String, Object> vars = new HashMap<String, Object>();
				System.out.println("search_name," + search_name);
				System.out.println("params," + params);
				if(search_name!=null && !StringUtils.isEmpty(search_name)){
					System.out.println("search_name11," + search_name);
					vars.put("search_name", search_name);	
				}
				
				Map mapObj = JSONObject.parseObject(params, Map.class);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
				if (null != mapObj.get("startDate") || null != mapObj.get("endDate")) {
					System.out.println("startDate===" + String.valueOf(mapObj.get("startDate")));
					System.out.println("endDate===" + String.valueOf(mapObj.get("endDate")));
					String startDate = String.valueOf(mapObj.get("startDate"));
					String endDate = String.valueOf(mapObj.get("endDate"));
					if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
						vars.put("startDate", startDate);
						vars.put("endDate", endDate);
					}
				} else {
					exportMsg(response, "startDate and endDate is null");
				}

				if (null != mapObj.get("tmzt")) {

					String tmzt = String.valueOf(mapObj.get("tmzt"));

					vars.put("tmzt", Arrays.asList(tmzt.split(",")));
				} else {
					exportMsg(response, "tmzt is null");
				}

				if (null != mapObj.get("jgid")) {
					Boolean jgid = Boolean.valueOf(String.valueOf(mapObj.get("jgid")));
					if (!StringUtils.isEmpty(jgid) && jgid) {
						vars.put("jgid", jgid);
						indexGroup++;
					}

				}

				if (null != mapObj.get("sjdw")) {
					Boolean sjdw = Boolean.valueOf(String.valueOf(mapObj.get("sjdw")));
					if (!StringUtils.isEmpty(sjdw) && sjdw) {
						vars.put("sjdw", sjdw);
						indexGroup++;
					}
				}

//				if (null != mapObj.get("xmzwm")) {
//					Boolean xmzwm = Boolean.valueOf(String.valueOf(mapObj.get("xmzwm")));
//					if (!StringUtils.isEmpty(xmzwm) && xmzwm) {
//						vars.put("xmzwm", xmzwm);
//						indexGroup++;
//					}
//
//				}
				if (null != mapObj.get("item")) {
					Boolean item = Boolean.valueOf(String.valueOf(mapObj.get("item")));
					if (!StringUtils.isEmpty(item) && item) {
						vars.put("item", item);
						indexGroup++;
					}

				}				
				if (null != mapObj.get("lbmc")) {
					Boolean lbmc = Boolean.valueOf(String.valueOf(mapObj.get("lbmc")));
					if (!StringUtils.isEmpty(lbmc) && lbmc) {
						vars.put("lbmc", lbmc);
						indexGroup++;
					}

				}

				if (null != mapObj.get("sjdw")) {
					Boolean sjdw = Boolean.valueOf(String.valueOf(mapObj.get("sjdw")));
					if (!StringUtils.isEmpty(sjdw) && sjdw) {
						vars.put("sjdw", sjdw);
						indexGroup++;
					}
				}

				if (null != mapObj.get("yqbh")) {
					Boolean yqbh = Boolean.valueOf(String.valueOf(mapObj.get("yqbh")));
					if (!StringUtils.isEmpty(yqbh) && yqbh) {
						vars.put("yqbh", yqbh);
						indexGroup++;
					}
				}

				if (null != mapObj.get("jyr")) {
					Boolean jyr = Boolean.valueOf(String.valueOf(mapObj.get("jyr")));

					if (!StringUtils.isEmpty(jyr) && jyr) {
						vars.put("jyr", jyr);
						indexGroup++;
					}
				}

				if (null != mapObj.get("jyks")) {
					Boolean jyks = Boolean.valueOf(String.valueOf(mapObj.get("jyks")));
					if (!StringUtils.isEmpty(jyks) && jyks) {
						vars.put("jyks", jyks);
						indexGroup++;
					}
				}

				if (indexGroup < 1) {
					exportMsg(response, "CATEGORY_PARAMS is null");
				}
				for (Map.Entry<String, Object> entry : vars.entrySet()) {
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
				}
				String condition = JSON.toJSONString(vars);
				List<ItemExcelReport> datas=null;
				String result= reportLisClient.itemExportExcel(vars);
				//System.out.println("results is"+result);
				datas=JSONObject.parseArray(result, ItemExcelReport.class); 
				System.out.println(datas.get(1));
				 // 表头
				 List<String> headerList = new ArrayList<String>();
				 headerList.add("机构名称");
				 headerList.add("送检医院");
				 headerList.add("姓名");
				 headerList.add("性别");
				 headerList.add("条码");
				 headerList.add("条码状态");
				 headerList.add("送检日期");
				 headerList.add("检验组合项");
				 headerList.add("仪器名称");
				 headerList.add("检验人");
				 headerList.add("检验科室");
				 headerList.add("申请模式");
				 headerList.add("病人类型");		 
				 headerList.add("机构ID");
				 headerList.add("送检医院ID");
				 headerList.add("类别名称");
				 headerList.add("检查组合明细项");
				 headerList.add("查询条件");
				
		

				
				
				
				 // 数据
				 List<List<Object>> objects = new ArrayList<List<Object>>();
				 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				 for (int i = 0; i < datas.size(); i++) {
				 ItemExcelReport bean = datas.get(i);
				
				 List<Object> dataA = new ArrayList<Object>();
				 dataA.add("" + bean.getJgmc());
				 dataA.add("" + bean.getSjdwmc());
				 dataA.add("" + bean.getBrxm());
				 dataA.add("" + bean.getSexmc());
				 dataA.add("" + bean.getTmh());
				 dataA.add("" + bean.getTmztmc());
				 dataA.add("" + bean.getZxsj());
				 dataA.add("" + bean.getZhmc());
				 
				 dataA.add("" + bean.getSbmc());
				 dataA.add("" + bean.getJyrmc());
				 dataA.add("" + bean.getKsdmmc());
				 dataA.add("" + bean.getSqmsmc());
				 dataA.add("" + bean.getBrlxmc());
				 dataA.add("" + bean.getJgid());
				 dataA.add("" + bean.getSjdw());
				 dataA.add("" + bean.getLbmc());
				 dataA.add("" + bean.getXmzwm());
				 dataA.add(condition);
				
				 objects.add(dataA);
				 }
				 
				//汇总
				 List<ItemAggrExcelReport> aggs=null;
				 String agg= reportLisClient.itemAggrExportExcel(vars);
				 aggs=JSONObject.parseArray(agg, ItemAggrExcelReport.class); 
				 
				 
				 // 汇总表头
				 List<String> aggsHeaderList = new ArrayList<String>();
				 aggsHeaderList.add("机构名称");
				 aggsHeaderList.add("A送检单位");
				 aggsHeaderList.add("B组合明细项");
				 aggsHeaderList.add("C仪器编号");
				 aggsHeaderList.add("D检验人");
				 aggsHeaderList.add("E检验科室");
				 aggsHeaderList.add("F类别名称");
				 aggsHeaderList.add("Z汇总");
				 aggsHeaderList.add("查询条件");
				 
				 // 数据
				 List<List<Object>> aggObjects = new ArrayList<List<Object>>();
				 for (int i = 0; i < aggs.size(); i++) {
					 ItemAggrExcelReport bean = aggs.get(i);
					 List<Object> dataA = new ArrayList<Object>();
					 dataA.add("" + bean.getJgmc());
					 dataA.add("" + bean.getSjdwmc());
					 dataA.add("" + bean.getXmzwm());
					 dataA.add("" + bean.getSbmc());
					 dataA.add("" + bean.getJyrmc());
					 dataA.add("" + bean.getKsdmmc());
					 dataA.add("" + bean.getLbmc());
					 dataA.add("" + bean.getEncount());
					 dataA.add(condition);
					 aggObjects.add(dataA);
				 }
				 ExcelCreateUtil excel = new ExcelCreateUtil();
				 HSSFWorkbook wb = excel.excelCreateUtil(headerList, objects,aggsHeaderList,aggObjects);
				 ouputStream = response.getOutputStream();
				 wb.write(ouputStream);
				 ouputStream.flush();

			} else {
				exportMsg(response, "params map is null");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ouputStream != null) {
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
