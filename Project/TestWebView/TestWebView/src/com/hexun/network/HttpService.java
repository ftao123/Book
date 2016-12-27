package com.hexun.network;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.util.Log;

public class HttpService {

	/** 连接或读取超时单位毫秒 */
	public static final int CONNECTION_TIMEOUT = 30000;// 15000
	/** 数据处理范围1024K */
	public static final int MAX_DATA_LENGTH = 1024 * 1024;

	public static String httpGet(String url, String queryString, String cookie)
			throws Exception {

		if (isNullEmptyBlank(url)) {
			return "url不能为空";
		}
		if (!isNullEmptyBlank(queryString)) {
			url += ("?" + queryString);
		}

		URL urlPath = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			urlPath = new URL(url);
			i("httpGet", "urlPath>>>>>" + urlPath);
			conn = (HttpURLConnection) urlPath.openConnection();
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设置连接主机超时（单位：毫秒）
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			// 设置从主机读取数据超时（单位：毫秒）
			conn.setReadTimeout(CONNECTION_TIMEOUT);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod("GET");
			if (!isNullEmptyBlank(cookie)) {
				conn.setRequestProperty("Cookie", cookie);
				i("httpGet", "cookie>>>>>" + cookie);
			}
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				is = conn.getInputStream();
				String str = readData(is, "UTF-8");
				e("httpGet", "str>>>>>" + str);
				return str;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
				if (conn != null)
					conn.disconnect();
			} catch (Exception e) {
			} finally {
				is = null;
				conn = null;
			}
		}
		return null;
	}

	public static String httpsPost(String url, String queryString, String cookie)
			throws Exception {

		if (isNullEmptyBlank(url)) {
			return "url不能为空";
		}

		URL urlPath = null;
		HttpsURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;

		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null,
					new TrustManager[] { new MyX509TrustManager() },
					new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
					.getSocketFactory());
			HttpsURLConnection
					.setDefaultHostnameVerifier(new MyHostnameVerifier());
			urlPath = new URL(url);
			conn = (HttpsURLConnection) urlPath.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设置连接主机超时（单位：毫秒）
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			// 设置从主机读取数据超时（单位：毫秒）
			conn.setReadTimeout(CONNECTION_TIMEOUT);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			if (!isNullEmptyBlank(cookie)) {
				conn.setRequestProperty("Cookie", cookie);
				i("httpsPost", "cookie>>>>>" + cookie);
			}
			if (!isNullEmptyBlank(queryString)) {
				os = conn.getOutputStream();
				os.write(queryString.getBytes("UTF-8"));
				os.flush();
				i("httpsPost", url + queryString);
			}
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				is = conn.getInputStream();
				String str = readData(is, "UTF-8");
				e("httpsPost", "str>>>>>" + str);
				return str;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (conn != null)
					conn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				os = null;
				is = null;
				conn = null;
			}
		}
		return null;
	}

	public static String httpPost(String url, String queryString)
			throws Exception {
		if (isNullEmptyBlank(url)) {
			return "url不能为空";
		}

		URL urlPath = null;
		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;

		try {
			urlPath = new URL(url);
			conn = (HttpURLConnection) urlPath.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			// 设置连接主机超时（单位：毫秒）
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			// 设置从主机读取数据超时（单位：毫秒）
			conn.setReadTimeout(CONNECTION_TIMEOUT);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			if (!isNullEmptyBlank(queryString)) {
				os = conn.getOutputStream();
				os.write(queryString.getBytes("UTF-8"));
				os.flush();
				i("httpPost", url + "?" + queryString);
			}
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				is = conn.getInputStream();
				String str = readData(is, "UTF-8");
				e("httpPost", "str>>>>>" + str);
				return str;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (conn != null)
					conn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				os = null;
				is = null;
				conn = null;
			}
		}
		return null;
	}

	private static String readData(InputStream inSream, String charsetName)
			throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = -1;
		while ((len = inSream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inSream.close();
		return new String(data, charsetName);
	}

	private static class MyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	private static class MyX509TrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	};

	public static void i(String tag, String msg) {
		if (tag == null || msg == null) {
			return;
		}
		Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (tag == null || msg == null) {
			return;
		}
		Log.e(tag, msg);
	}

	/**
	 * 判断字符串是否为空(包含null与"","    ")
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullEmptyBlank(String str) {
		if (str == null || "".equals(str) || "".equals(str.trim()))
			return true;
		return false;
	}
}