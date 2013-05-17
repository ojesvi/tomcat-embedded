package com.blogspot.btafarelo.embedded;

import java.util.ResourceBundle;

public class Config {
	
	private static final String BUNDLE_NAME = "com.blogspot.btafarelo.embedded.config";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Config() {
		
	}

	public static String get(Key key) {
		return RESOURCE_BUNDLE.getString(key.name());
	}
	
	public static int getInt(Key key) {
		return Integer.parseInt(RESOURCE_BUNDLE.getString(key.name()));
	}
	
	public static String getContext(int i) {
		return RESOURCE_BUNDLE.getString(Key.WEBAPP_CONTEXT.name() + "_" + i);
	}
	
	public static String getBaseDir(int i) {
		return RESOURCE_BUNDLE.getString(Key.WEBAPP_BASEDIR.name() + "_" + i);
	}
}
