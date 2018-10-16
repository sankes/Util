package com.shankes.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.Formatter;

public class MD5Util {
	public static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	public static String byteToHexString(byte b) {
		Formatter formatter = new Formatter();
		formatter.format("%02x", b);//以十六进制输出,2为指定的输出字段的宽度.如果位数小于2,则左端补0
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String MD5Encode(String origin){
		return MD5Encode(origin,"UTF-8");
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString ;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (TextUtils.isEmpty(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
			return null;
		}
		return resultString;
	}

}
