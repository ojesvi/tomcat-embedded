package com.blogspot.btafarelo.embedded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.catalina.LifecycleState;

public class Command {

	private Command() {

	}

	public static String send(final String cmd) throws UnknownHostException,
			IOException {
		String result = null;

		Socket socket = new Socket(Config.get(Key.HOST),
				Config.getInt(Key.PORT_SOCKET));

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(cmd);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		result = in.readLine();

		out.close();
		in.close();
		socket.close();

		return result;
	}

	public static boolean isAlive() throws UnknownHostException, IOException {
		return LifecycleState.STARTED.name().equals(send("echo"));
	}

	public static String stop() throws UnknownHostException, IOException {
		return send("shutdown");
	}
}
