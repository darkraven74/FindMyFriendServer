package ru.ifmo.findmyfriend.server;

import java.util.TimeZone;
import java.util.Calendar;

public class TimeUtils {
	private static final TimeZone timeZone = TimeZone.getTimeZone("GMT");

	public static long getCurrentTimeGmt() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.getTimeInMillis();
	}
}