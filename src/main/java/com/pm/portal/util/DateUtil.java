package com.pm.portal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
    public static String dateToStamp(String s,String f) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(f);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime()/1000;
        res = String.valueOf(ts);
        return res;
    }
    
    public static void main(String[] args) {
		try {
			System.out.println(dateToStamp("1958/12/27 00:00:00","yyyy/MM/dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
