package com.blogspot.btafarelo.embedded;

import java.io.IOException;
import java.net.UnknownHostException;

public class Stop {

	public static void main(String[] args) throws UnknownHostException, IOException {
		if (Command.isAlive()) {
			Command.stop();
			return;
		}
	}
}
