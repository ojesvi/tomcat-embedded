package com.blogspot.btafarelo.embedded;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.catalina.startup.Tomcat;

public class Watchdog extends Thread {

	private ServerSocket server;

	private Tomcat tomcat;

	public Watchdog(Tomcat tomcat) {
		this.tomcat = tomcat;
	}

	@Override
	public void run() {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;

		String cmd = null;

		try {
			server = new ServerSocket(Config.getInt(Key.PORT_SOCKET));

			while (!"shutdown".equals(cmd)) {
				socket = server.accept();

				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				cmd = in.readLine();

				if ("echo".equals(cmd)) {
					out = new PrintWriter(socket.getOutputStream(), true);
					out.println(tomcat.getServer().getState());
					out.close();
				}

				socket.close();
			}

			if (tomcat != null)
				tomcat.stop();
		} catch (Exception ex) {
			throw new RuntimeException("Watchdog.run()", ex);
		}
	}
}
