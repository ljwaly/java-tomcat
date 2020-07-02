package com.ljw.server.controller;

import com.ljw.server.Request;
import com.ljw.server.Response;

public class XmlServlet implements BaseServlet{

	@Override
	public void doGet(Request request, Response response) {

		// 获取参数
		String uname = request.getParamter("name");
		String password = request.getParamter("pwd");

		// 打印结果
		response.println("<html>");
		response.println("<head>");
		response.println("<title>");
		response.println("HTTP响应");
		response.println("</title>");
		response.println("</head>");
		response.println("<body>");
		response.println("Hello HTTP Server!" + "name:" + uname + ",pwd:" + password);
		response.println("</body>");
		response.println("</html>");

	}

	@Override
	public void doPost(Request request, Response response) {
		doGet(request, response);
	}

}
