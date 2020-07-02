package com.ljw.util;

import java.io.IOException;
import java.net.Socket;

public class CloseUtil {

	public static void closeSocket(Socket client) {

		try {
			if (client == null) {
				return;
			}
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
