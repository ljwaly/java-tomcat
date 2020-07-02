package com.ljw.server.controller;

import com.ljw.server.Request;
import com.ljw.server.Response;

public class JsonServlet implements BaseServlet{

	@Override
	public void doGet(Request request, Response response) {
		response.print("{\"code\":\"200\", \"desc\":\"success\", \"abc\":\"ljwabc\"}");
		
	}

	@Override
	public void doPost(Request request, Response response) {
		doGet(request, response);
		
	}

}
