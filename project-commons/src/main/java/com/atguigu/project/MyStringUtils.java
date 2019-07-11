package com.atguigu.project;

public class MyStringUtils {
	public static void test() {
		System.out.println("test");
	}

	public static boolean isEmpty(String inStr) {
		if(inStr == null && "".equals(inStr))
			return true;
		return false;
	}

}
