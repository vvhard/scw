package com.atguigu.project;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyStringUtils {
	

	public static boolean isEmpty(String inStr) {
		if(inStr == null && "".equals(inStr))
			return true;
		return false;
	}
	// yyyy-MM-dd 
	public static String formatSimpleDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

}
