package com.xs.mpandroidchardemo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Simon on 2017/4/3.
 */
public class TimeHelper {

    //Date date1=new Date(2015-1900,11,30,23,59,59);

    /**
     * 格式：当前时间（yyyy-MM-dd HH:mm:ss）
     */
    private final static ThreadLocal<SimpleDateFormat> dateFormatDetailCurrentTime = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        }
    };
    private final static ThreadLocal<SimpleDateFormat> todayFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        }
    };

    /**
     * 获取当前详细时间
     * @return
     */
    public static String getDetailCurrDate() {
        Date date = new Date(System.currentTimeMillis());
        return dateFormatDetailCurrentTime.get().format(date);
    }

    public static String getToday() {
        Date date = new Date(System.currentTimeMillis());
        return todayFormat.get().format(date);
    }

    /**
     *
     * @return
     */
    public static int getBetweenMinutes() {

        Date date1 = new Date(getTimesmorning());
        Date date2 = new Date(System.currentTimeMillis());

        if (date1 == null || date2 == null) {
            return -1;
        }

        int betweenMinutes; //两个时间间隔分钟数
        int betweenHours; //两个时间间隔小时数

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);

        if (c1.after(c2)) { //确保第二个时间一定大于第一个时间

            c2.setTime(date1);
            c1.setTime(date2);
        }
        betweenHours = c2.get(Calendar.HOUR_OF_DAY)
                - c1.get(Calendar.HOUR_OF_DAY);
        betweenMinutes = c2.get(Calendar.MINUTE) - c1.get(Calendar.MINUTE);

        betweenMinutes += betweenHours * 60;

        return betweenMinutes;
    }

    private static long getTimesmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
