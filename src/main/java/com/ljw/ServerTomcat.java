package com.ljw;

import com.ljw.service.ServerMachine;

public class ServerTomcat {
	public static void main(String[] args) {
		ServerMachine server = new ServerMachine(8888);
		server.start();
	}
}
