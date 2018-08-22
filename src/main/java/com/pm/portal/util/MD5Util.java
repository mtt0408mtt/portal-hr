package com.pm.portal.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

	public static String md5(String src) {
		return DigestUtils.md5Hex(src);
	}

	private static final String salt = "4q3w2e1r";

	public static String inputPassToFormPass(String inputPass) {
		String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
		System.out.println(str);
		return md5(str);
	}

	public static String formPassToDBPass(String formPass, String salt) {
		String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
		return md5(str);
	}

	public static String inputPassToDbPass(String inputPass, String saltDB) {
		String formPass = inputPassToFormPass(inputPass);
		String dbPass = formPassToDBPass(formPass, saltDB);
		return dbPass;
	}

	public static void main(String[] args) {
		// System.out.println(inputPassToFormPass("123456"));//78411406ec8925f1e07555989db84e2d
		// System.out.println(formPassToDBPass(inputPassToFormPass("123456"),
		// "1q2w3e4r"));//97847cd65a931e22e009ed97796f2a6c
		System.out.println(inputPassToDbPass("test", "4q3w2e1r"));// 97847cd65a931e22e009ed97796f2a6c
		
		
	}

}
