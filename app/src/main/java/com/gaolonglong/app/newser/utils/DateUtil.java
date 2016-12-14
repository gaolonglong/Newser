package com.gaolonglong.app.newser.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gaohailong on 2016/11/30.
 */

public class DateUtil {
    /**
     * 12小时制: "yyyy-MM-dd hh:mm:ss"
     * 24小时制: "yyyy-MM-dd HH:mm:ss"
     */
    public static String getDate(int day){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,Integer.parseInt("-" + day));
        Date date = calendar.getTime();

        return format.format(date);
    }

}
