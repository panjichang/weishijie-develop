package com.pan.simplepicture.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.TreeMap;

public class MD5Utils {
	public static String MD5(String str) {
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest
					.getInstance("MD5");
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";

	}

	private static String toHexString(byte[] b) { // String to byte
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			int j = 0xFF & b[i];
			if (j <= 15) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(j));
		}
		return sb.toString();
	}
	private static final String KEY="7763079ba6abf342a99ab5a1dfa87ba8";
	public static String getAccessToken(TreeMap<String, String> paramTreeMap) {
		String str1 = "";
		Iterator localIterator = paramTreeMap.keySet().iterator();
		while (localIterator.hasNext()) {
			String str3 = (String) localIterator.next();
			str1 = str1 + str3 + "=" + (String) paramTreeMap.get(str3) + "&";
		}
		String str2 = str1.substring(0, -1 + str1.length());
		return digest(str2 + KEY);
	}
	
	static final String HEXES = "0123456789abcdef";

	  public static String digest(String paramString)
	  {
	    try
	    {
	      MessageDigest localMessageDigest = MessageDigest.getInstance("md5");
	      localMessageDigest.update(paramString.getBytes());
	      String str = getHex(localMessageDigest.digest());
	      return str;
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	    return null;
	  }

	  private static String getHex(byte[] paramArrayOfByte)
	  {
	    if (paramArrayOfByte == null)
	      return null;
	    StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
	    int i = paramArrayOfByte.length;
	    for (int j = 0; j < i; j++)
	    {
	      int k = paramArrayOfByte[j];
	      localStringBuilder.append(HEXES.charAt((k & 0xF0) >> 4)).append(HEXES.charAt(k & 0xF));
	    }
	    return localStringBuilder.toString();
	  }
}
