package com.ljw.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.ljw.util.ServerUtil;

public class Request {
	// 请求方法
	private String method;

	// 请求资源
	private String url;

	// 请求参数
	private Map<String, List<String>> map;

	// 内部
	private static final String CRLF = "\r\n";
	private InputStream is;
	private String requestInfo;

	public Request() {
		method = "";
		url = "";
		map = new HashMap<String, List<String>>();
		requestInfo = "";
	}

	public Request(InputStream is) {
		this();
		this.is = is;
		try {
			byte[] data = new byte[20480];
			int len = is.read(data);
			requestInfo = new String(data, 0, len);
			parseRequestInfo();
		} catch (IOException e) {
			return;
		}
		// 分析头信息
		parseRequestInfo();
	}

	public Request(Socket client) {
		this();

		try {
			
			StringBuilder sb = new StringBuilder("");
			
			this.is = client.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is)); 
			
			String buffer = bufferedReader.readLine();
			while (buffer != null) {
				sb.append(buffer);
				buffer = bufferedReader.readLine();
			}
			requestInfo = sb.toString();
			
			
			parseRequestInfo();
		} catch (IOException e) {
			return;
		}
		// 分析头信息
		parseRequestInfo();
	}

	private void parseRequestInfo() {

		if (null == requestInfo || "".equals(requestInfo.trim())) {
			return;
		}

		/**
		 * ===================================================
		 * 从信息的首行分析出：请求方式，请求路径，请求参数（get可能有） 如：GET /index.html?name=root&password=1988ljw
		 * 
		 * 如果为post方式，请求参数则在正文中
		 * 
		 * 
		 * ===================================================
		 */

		String paramString = "";// 接收请求参数
		String firstLion = requestInfo.substring(0, requestInfo.indexOf(CRLF));

		int indexMethod = firstLion.indexOf("/");
		this.method = firstLion.substring(0, firstLion.indexOf("/")).trim();
		String urlStr = firstLion.substring(indexMethod, firstLion.indexOf("HTTP/")).trim();

		// 1.获取并配置url和params
		if (this.method.equalsIgnoreCase("POST")) {
			url = urlStr;
			paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();

		} else if (this.method.equalsIgnoreCase("GET")) {
			if (urlStr.contains("?")) {// 是否存在参数
				String[] urlArray = urlStr.split("\\?");
				this.url = urlArray[0];
				paramString = urlArray[1];
			} else {
				this.url = urlStr;
			}
		}

		// 2.将params的字符串填入map中
		if (paramString == null || "".equals(paramString)) {
			return;
		}

		paramParams(paramString);

	}

	private void paramParams(String paramString) {
		// 分割字符串，将字符串分割为数组
		StringTokenizer token = new StringTokenizer(paramString, "&");
		while (token.hasMoreElements()) {
			String keyValue = token.nextToken();
			String[] keyValues = keyValue.split("=");
			if (keyValues.length == 1) {
				keyValues = Arrays.copyOf(keyValues, 2);
				keyValues[1] = null;
			}
			String key = keyValues[0].trim();
			String value = keyValues[1] == null ? "" : decode(keyValues[1].trim(), ServerUtil._CODE);
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<String>());
			}
			map.get(key).add(value);
		}
	}

	/**
	 * 根据页面的name获取对应的多个值
	 * 
	 * @param key
	 * @return
	 */
	public String[] getParamterValues(String name) {
		List<String> values = null;
		if ((values = map.get(name)) == null) {
			return null;
		} else {
			return values.toArray(new String[0]);
		}
	}

	/**
	 * 根据页面的name获取对应的单个值
	 * 
	 * @param key
	 * @return
	 */
	public String getParamter(String name) {
		String[] values = getParamterValues(name);
		if (values == null) {
			return null;
		} else {
			return values[0];
		}
	}

	public String decode(String value, String code) {
		try {
			return java.net.URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}
		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
