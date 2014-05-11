package utils;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtils {
    private static final TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");

    public static long getCurrentTimeGmt() {
        Calendar calendar = Calendar.getInstance(gmtTimeZone);
        return calendar.getTimeInMillis();
    }
}
