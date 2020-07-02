package com.ljw.server.controller;

import com.ljw.server.Request;
import com.ljw.server.Response;

public interface BaseServlet {
	public void doGet(Request request, Response response);
	public void doPost(Request request, Response response);
}
