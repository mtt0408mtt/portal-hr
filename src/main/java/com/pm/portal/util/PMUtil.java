package com.pm.portal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PMUtil {
	
	
	
	public static String UTCStringtODefaultString(String UTCString) {
		try {
			UTCString = UTCString.replace("Z", " UTC");
			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
			SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = utcFormat.parse(UTCString);
			return defaultFormat.format(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
			return null;
		}
	}
	
	public static String UTCStringtODefaultStringDay(String UTCString) {
		try {
			UTCString = UTCString.replace("Z", " UTC");
			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
			SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = utcFormat.parse(UTCString);
			return defaultFormat.format(date);
		} catch (ParseException pe) {
			pe.printStackTrace();
			return null;
		}
	}

}
