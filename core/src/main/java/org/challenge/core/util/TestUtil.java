package org.challenge.core.util;

import java.util.UUID;
public class TestUtil {

	public static String randomString(){
		return UUID.randomUUID().toString();
	}

	public static String randomString(int size){
		return randomString().substring(0, size);
	}
}
