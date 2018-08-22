package com.pm.portal.controller.demo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class Demo4 {

	public static void main(String[] args) {
		// String strDStart="2018-08-12T04:59:10.737Z";
		// SimpleDateFormat f1 = new
		// SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		// f1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		//
		//
		//
		// System.out.println(PMUtil.UTCStringtODefaultStringDay(strDStart));

		// String addZeroForNum = addZeroForNum("2121", 7);
		//
		// System.out.println(addZeroForNum);

		Demo4 t = new Demo4();
		System.out.println(t.getNum());
		System.out.println(t.getNum());
		System.out.println(t.getNum());

	}

	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}

	private static Map<String, String> map = new HashMap<String, String>();
	private static String STATNUM = "000001";

	public String getYearAndMonth() {
		StringBuffer sb = new StringBuffer();
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR)-2000;
		int month = cal.get(Calendar.MONTH) + 1;
		sb.append(year);
		if (month < 10) {
			sb.append("0" + month);
		} else {
			sb.append(month);
		}
		return sb.toString();
	}

	public String getLastSixNum(String s) {
		String rs = s;
		int i = Integer.parseInt(rs);
		i += 1;
		rs = "" + i;
		for (int j = rs.length(); j < 6; j++) {
			rs = "0" + rs;
		}
		return rs;
	}

	public synchronized String getNum() {
		String yearAMon = getYearAndMonth();
		String last6Num = map.get(yearAMon);
		if (last6Num == null) {
			map.put(yearAMon, STATNUM);
		} else {
			map.put(yearAMon, getLastSixNum(last6Num));
		}
		return yearAMon + map.get(yearAMon);
	}

}
