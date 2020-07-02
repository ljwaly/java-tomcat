package com.ljw.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import com.ljw.util.ServerUtil;

/**
 * 封装相应信息
 * 
 * @author PC
 *
 */
public class Response {

	private static final String BLANK = " ";

	private static final String CRLF = "\r\n";

	// 存储正文
	private StringBuilder content;
	// 存储头信息
	private StringBuilder headInfo;
	// 存储正文长度
	private int len = 0;

	private boolean falg = false;
	private BufferedWriter bw;

	public Response() {
		content = new StringBuilder();
		headInfo = new StringBuilder();
	}

	public Response(OutputStream os) {
		this();
		content = new StringBuilder();
		headInfo = new StringBuilder();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}

	public Response(Socket client) {
		this();
		content = new StringBuilder();
		headInfo = new StringBuilder();
		try {
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			falg = true;
		}
	}

	/**
	 * 构建正文
	 * 
	 * @param info
	 * @return
	 */
	public Response print(String info) {
		content.append(info);
		len += info.getBytes().length;
		return this;
	}

	/**
	 * 构建正文+回车
	 * 
	 * @param info
	 */
	public Response println(String info) {
		content.append(info).append(CRLF);
		len += info.getBytes().length;
		return this;
	}

	private void createHeadInfo(int code) {
		// 1.HTTP协议，状态码，描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch (code) {
		case 200:
			headInfo.append("OK");
			break;
		case 404:
			headInfo.append("NOT FOUND");
			break;
		case 500:
			headInfo.append("SERVER ERROR");
			break;

		default:
			break;
		}
		headInfo.append(CRLF);
		// 2.响应头
		headInfo.append("Server:lijinwu Server/0.0.1").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		// 读取正文类型，必填
		headInfo.append("Content-type：text/html;charset=" + ServerUtil._CODE).append(CRLF);
		// 读取正文长度，必填
		headInfo.append("Content-Length:").append(len).append(CRLF);
		headInfo.append(CRLF);// 添加空行
	}

	void pushToCilent(int code) throws IOException {
		if (falg) {
			code = 500;
		}
		// Headers初始化
		createHeadInfo(code);
		// 头信息+分隔符
		bw.append(headInfo.toString());
		// 正文
		bw.append(content.toString());
		bw.flush();
	}

	public void close() throws IOException {
		bw.close();
	}
}
