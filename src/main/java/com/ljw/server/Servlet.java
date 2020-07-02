package com.ljw.server;

public class Servlet {

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

	public void doPost(Request request, Response response) {
		doGet(request, response);
	}

	public void service(Request request, Response response) {
		doGet(request, response);

	}
}
