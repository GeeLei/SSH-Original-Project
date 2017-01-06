package com.huanke.sshshell.util;

import java.sql.Timestamp;

public class TimeUtils {

	public static Timestamp nowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}
