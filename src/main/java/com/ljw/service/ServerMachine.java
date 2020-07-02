package com.ljw.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.ljw.server.Dispatcher;
import com.ljw.util.CloseUtil;

public class ServerMachine {

	private ServerSocket server;

	private int port;
	private boolean flag = true;

	/**
	 * 启动方法
	 */
	public void start() {
		try {

			server = new ServerSocket(port);
			flag = true;
			this.receive();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 接收客户端
	 */
	private void receive() {
		Socket client = null;
		try {
			System.out.println(1);

			while (flag) {
				client = server.accept();
				System.out.println(2);

				Thread thread = new Thread(new Dispatcher(client));
				thread.start();
				System.out.println(3);
			}

		} catch (Exception e) {
			System.out.println("error");
			CloseUtil.closeSocket(client);
			stop();
		}
	}

	/**
	 * 停止服务器
	 */
	public void stop() {
		flag = false;

	}

	
	
	
	
	
	
	
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServerMachine(int port) {
		this.port = port;
	}

}
