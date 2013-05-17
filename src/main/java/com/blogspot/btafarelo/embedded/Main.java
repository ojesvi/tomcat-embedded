package com.blogspot.btafarelo.embedded;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Main {

	private static Tomcat tomcat = new Tomcat();

	public static void main(String[] args) throws Exception {
		new Main();
	}

	public Main() throws Exception {
		new Watchdog(tomcat).start();

		start();
	}

	private void start() throws Exception {
		tomcat.setPort(Integer.valueOf(Config.getInt(Key.PORT_HTTP)));

		int webapp_length = Config.getInt(Key.WEBAPP_LENGTH);

		for (int i = 0; i < webapp_length; i++)
			tomcat.addWebapp(Config.getContext(i),
					new File(Config.getBaseDir(i)).getAbsolutePath());

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (tomcat != null)
					try {
						tomcat.stop();
						tomcat = null;
					} catch (LifecycleException e) {
						e.printStackTrace();
					}
			}
		});

		StringBuilder url = new StringBuilder();
		url.append("http://");
		url.append(Config.get(Key.HOST));
		url.append(":");
		url.append(Config.get(Key.PORT_HTTP));
		url.append(Config.get(Key.HOMEPAGE));
		
		Desktop.getDesktop().browse(URI.create(url.toString()));
		
		tomcat.start();
		tomcat.getServer().await();
	}
}