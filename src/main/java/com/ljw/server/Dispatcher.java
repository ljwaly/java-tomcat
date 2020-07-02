package com.ljw.server;

import java.io.IOException;
import java.net.Socket;

import com.ljw.server.controller.BaseServlet;
import com.ljw.server.controller.JsonServlet;
import com.ljw.util.CloseUtil;

public class Dispatcher implements Runnable {

	private Socket client;
	private Response response;
	private Request request;

	private int code = 200;

	public Dispatcher(Socket client) {

		this.client = client;
		try {
			this.request = new Request(client.getInputStream());
			this.response = new Response(client.getOutputStream());
		} catch (IOException e) {
			this.code = 500;
		}

	}

	@Override
	public void run() {
		BaseServlet servlet = getServlet();
		servlet.doGet(request, response);
		try {
			response.pushToCilent(code);// 推送结果

		} catch (IOException e) {
		}



		CloseUtil.closeSocket(client);
	}

	private BaseServlet getServlet() {
		
		return new JsonServlet();
	}

}
