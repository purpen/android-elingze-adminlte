package com.thn.basemodule.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lilin on 2017/7/25.
 */

public class DateUtil {
    private int interval =0;
    /**
     * 某日期的前几天或者后几天
     * 正表前几天,负表后几天
     * @return
     */
    public static String getSpecifiedDayBefore(Date date, int interval){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-interval);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获取某日期的前几月或者后几月
     * 正表示前几月,负表示后几月
     * @param date
     * @param interval
     * @return
     */
    public static String getSpecifiedMonthBefore(Date date, int interval){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH,month-interval);
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获取某日期的前几月或者后几月
     * 正表示前几月,负表示后几月
     * @param dateStr
     * @param interval
     * @return
     */
    public static String getSpecifiedMonthBefore(String dateStr, int interval){
        Calendar c = Calendar.getInstance();
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            c.setTime(date);
            int month = c.get(Calendar.MONTH);
            c.set(Calendar.MONTH,month-interval);
            return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据时间戳获得日期
     */
    public static String getDateByTimestamp(long timestamp){
        timestamp = timestamp*1000;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(new Date(timestamp));
        return simpleDateFormat.format(c.getTime());
    }
}
