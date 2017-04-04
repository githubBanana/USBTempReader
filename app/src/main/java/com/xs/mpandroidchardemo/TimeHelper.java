package com.xs.mpandroidchardemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/4/3.
 */
public class TimeHelper {

    //Date date1=new Date(2015-1900,11,30,23,59,59);

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
