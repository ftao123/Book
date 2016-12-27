package com.hexun.network;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class QueryString implements Serializable {

	private String TRANSTYPE = "transType";
	private String CUSTID = "custId";
	private String MERLD = "merId";
	private String SESSIONKEY = "sessionKey";
	private String VERIFYCODE = "verifyCode";
	private String REQTYPE = "reqType";
	private String transType;
	private String merId;
	private String custId;
	private String sessionKey;
	private String reqType;

	private static final long serialVersionUID = 1L;

	public QueryString(String custId, String sessionKey) {
		this.transType = "CM010";
		this.merId = "600084";
		this.custId = custId;
		this.sessionKey = sessionKey;
		this.reqType = "M";

	}

	public String getRequestData() {
		StringBuffer param = new StringBuffer();
		return param
				.append(TRANSTYPE).append("=").append(transType).append("&")
				.append(MERLD).append("=").append(merId).append("&")
				.append(REQTYPE).append("=").append(reqType).append("&")
				.append(CUSTID).append("=").append(custId).append("&")
				.append(SESSIONKEY).append("=").append(sessionKey).append("&")
				.append(VERIFYCODE).append("=").append(getMD5Value())
				.toString();

	}

	private String getMD5Value() {
		return getMD5Value(MD5_SEED + getCurrentDate() + "transType:"
				+ transType + ";" + "merId:" + merId + ";" + "reqType:"
				+ reqType + ";" + "custId:" + custId + ";" + "sessionKey:"
				+ sessionKey);
	}

	/**
	 * 文忠接口需要的秘钥
	 */
	public static final String MD5_SEED = "&@IISOa(#)S00230*_DSO";// HEXUNFSD

	public String getCurrentDate() {
		StringBuffer buffer = new StringBuffer();
		Calendar now = Calendar.getInstance();
		String monthValue = null;
		String dayValue = null;

		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int DAY_OF_MONTH = now.get(Calendar.DAY_OF_MONTH);
		if (month < 10) {
			monthValue = "0" + String.valueOf(month);
		} else {
			monthValue = String.valueOf(month);
		}
		if (DAY_OF_MONTH < 10) {
			dayValue = "0" + String.valueOf(DAY_OF_MONTH);
		} else {
			dayValue = String.valueOf(DAY_OF_MONTH);
		}
		return buffer.append(year).append(monthValue).append(dayValue)
				.toString();
	}

	public static String getMD5Value(String preValue) {
		String resultString = null;
		if (preValue != null && preValue.length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				resultString = byteToString(md.digest(preValue.getBytes()));

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}