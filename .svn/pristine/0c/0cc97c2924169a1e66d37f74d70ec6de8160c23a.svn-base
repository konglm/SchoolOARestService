/*----------------------------------------------------------------
 *  Copyright (C) 2016山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：
 * 文件功能描述：
 *
 * 
 * 创建标识：
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

/**
 * @author konglm
 *
 */
public class HttpUtil {
	public static void main(String[] args) {
		String requestStr = "http://139.129.252.49:8080/visitor/vr/toSearch/sid/begybmzw3akm5rnni144roib";
		System.out.println(requestStr.substring(requestStr.indexOf("sid") + 4, requestStr.length()));
	}

	/**
	 * 提交Http请求，post json数据
	 * 
	 * @param serviceUrl
	 *            接口地址
	 * @param inJson
	 *            提交的json
	 * @return 返回的json
	 */
	public static String postHttpJson(String serviceUrl, String inJson) {
		String outJson = "";
		try {
			// 创建url资源
			URL url = new URL(serviceUrl);
			// 建立http连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置允许输出
			conn.setDoOutput(true);

			conn.setDoInput(true);

			// 设置不用缓存
			conn.setUseCaches(false);
			// 设置传递方式
			conn.setRequestMethod("POST");
			// 设置维持长连接
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 设置文件字符集:
			conn.setRequestProperty("Charset", "UTF-8");
			// 转换为字节数组
			byte[] data = (inJson).getBytes("UTF-8");
			// 设置文件长度
			conn.setRequestProperty("Content-Length", String.valueOf(data.length));

			// 设置文件类型:
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

			// 开始连接请求
			conn.connect();
			OutputStream out = conn.getOutputStream();
			// 写入请求的字符串
			out.write(inJson.getBytes("UTF-8"));
			out.flush();
			out.close();

			System.out.println(conn.getResponseCode());

			// 请求返回的状态
			if (conn.getResponseCode() == 200) {
				System.out.println("连接成功");
				// 缓冲读取请求返回的数据
				InputStream in = new BufferedInputStream(conn.getInputStream());
				try {
					StringBuilder sb = new StringBuilder();
					BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
					String line;
					while ((line = rd.readLine()) != null) {
						sb.append(line);
					}
					// 转成字符串
					outJson = sb.toString();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				System.out.println("no++");
			}

		} catch (Exception e) {

		}
		return outJson;
	}

	/**
	 * post方式请求服务器(https协议)
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static String postHttps(String url, String content)
			throws NoSuchAlgorithmException, KeyManagementException, IOException {
		String outJson = "";
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 设置传递方式
		conn.setRequestMethod("POST");
		// 设置文件字符集:
		conn.setRequestProperty("Charset", "UTF-8");
		// 设置文件类型:
		conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes("UTF-8"));
		// 刷新、关闭
		out.flush();
		out.close();

		// 请求返回的状态
		if (conn.getResponseCode() == 200) {
			System.out.println("连接成功");
			// 缓冲读取请求返回的数据
			InputStream in = new BufferedInputStream(conn.getInputStream());
			try {
				StringBuilder sb = new StringBuilder();
				BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				// 转成字符串
				outJson = sb.toString();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			System.out.println("no++");
		}
		return outJson;
	}

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	private static String readResponseString(HttpURLConnection conn) {
		BufferedReader reader = null;
		try {
			StringBuilder ret;
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = reader.readLine();
			if (line != null) {
				ret = new StringBuilder();
				ret.append(line);
			} else {
				return "";
			}

			while ((line = reader.readLine()) != null) {
				ret.append('\n').append(line);
			}
			return ret.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
		}
	}

}
